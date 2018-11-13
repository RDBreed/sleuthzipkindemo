package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Our custom trace filter which will ensure a conversation id can be passed along.
 * We load this filter after the Tracefilter (which is the filter which actually starts/continues/handles our traces & spans.
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
    //This ensures that the id is passed in our MCD for logging purposes
    //Can be replaced with spring.sleuth.log.slf4j.whitelisted-mdc-keys property in Greenwich release
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
