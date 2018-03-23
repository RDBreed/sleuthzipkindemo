package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Span;
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
import org.springframework.web.filter.GenericFilterBean;

public class CustomTraceFilter extends GenericFilterBean {

  private final Tracer tracer;

  public static final String TAG_NAME = "X-B3-CONVID";

  public CustomTraceFilter(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String existingConversationId = ExtraFieldPropagation.get(TAG_NAME);
    Span span = tracer.currentSpan();
    if (existingConversationId == null) {
      //if field propagation is null, we will generate our own in the next few lines, thus starting a new conversation.
      existingConversationId = UUID.randomUUID().toString();
      ExtraFieldPropagation.set(TAG_NAME, existingConversationId);
    }
    //This ensures that the id is passed to zipkin
    span.tag(TAG_NAME, existingConversationId);
    //This ensures that the id is passed in our MCD for logging purposes
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
