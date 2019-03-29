package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Our custom trace filter which will ensure a conversation id can be passed along.
 * We load this filter after the Tracefilter (which is the filter which actually starts/continues/handles our traces & spans.
 * We still put the MDC ourselves, because unfortunately
 * {@link org.springframework.cloud.sleuth.log.Slf4jScopeDecorator} will be too late if the propagation field is added in this method...
 */
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
public class CustomTraceFilter extends GenericFilterBean {

    private final Tracer tracer;

    public static final String TAG_NAME = "X-B3-CONVID";

    public CustomTraceFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String existingConversationId = ExtraFieldPropagation.get(TAG_NAME);
        if (existingConversationId == null) {
            //if field propagation is null, we will generate our own in the next few lines, thus starting a new conversation.
            existingConversationId = UUID.randomUUID().toString();
            ExtraFieldPropagation.set(TAG_NAME, existingConversationId);
        }
        //By adding a tag to the current span, this ensures that the id is passed to zipkin
        tracer.currentSpanCustomizer().tag(TAG_NAME, existingConversationId);
        // Still needed as unfortunately Slf4jScopeDecorator will be too late when we just added the id...
        MDC.put(TAG_NAME, existingConversationId);
        //Will ensure the conversation id is also available in the response for the client
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (response != null) {
            response.addHeader(TAG_NAME, existingConversationId);
            //Enabling for a frontend that they may use this header
            response.addHeader("Access-Control-Expose-Headers", TAG_NAME);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
