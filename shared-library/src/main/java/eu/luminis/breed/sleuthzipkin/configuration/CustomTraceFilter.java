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
import org.apache.catalina.connector.RequestFacade;
import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;


public class CustomTraceFilter extends GenericFilterBean {

  private final Tracer tracer;

  private static final String TAG_NAME = "X-B3-CONVID";

  public CustomTraceFilter(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String existingConversationId;
    Span span = tracer.currentSpan();
    boolean isStartingConversation = hasConversationIdFromRequest(servletRequest);
    if(ExtraFieldPropagation.get(TAG_NAME) == null){
      //if field propagation is null, we will generate our own in the next few lines, thus starting a new conversation.
      isStartingConversation = true;
    }
    existingConversationId = getConversationIdFromFieldPropagation();
    //This ensures that the id is passed to zipkin
    span.tag(TAG_NAME, existingConversationId);
    //This ensures that the id is passed in our MCD for logging purposes
    MDC.put(TAG_NAME, existingConversationId);
    //Check if this is the start of the conversation, so that we can return a conversation id properly if needed
    if(isStartingConversation) {
      //Will ensure the conversation id is also available in the response for the client
      HttpServletResponse response = (HttpServletResponse) servletResponse;
      if (response != null) {
        response.addHeader(TAG_NAME, existingConversationId);
        //Enabling for a frontend that they may use this header
        response.addHeader("Access-Control-Expose-Headers", TAG_NAME);
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String getConversationIdFromFieldPropagation() {
    String existingConversationId;
    //If the trace already has a field propagation, we use it
    existingConversationId = ExtraFieldPropagation.get(TAG_NAME);
    //Else, we will generate it
    if (existingConversationId == null) {
      existingConversationId = UUID.randomUUID().toString();
      ExtraFieldPropagation.set(TAG_NAME, existingConversationId);
    }
    return existingConversationId;
  }

  private boolean hasConversationIdFromRequest(ServletRequest servletRequest) {
    String existingConversationId = null;
    if (servletRequest instanceof RequestFacade) {
      existingConversationId = ((RequestFacade) servletRequest)
          .getHeader(TAG_NAME);
    }
    if (existingConversationId != null) {
      // Set conversation id to field propagation.
      // This ensures that calls to consecutively services will also inherit this value correctly.
      ExtraFieldPropagation.set(TAG_NAME, existingConversationId);
      //A conversation has been started, thus returning true
      return true;
    }
    return false;
  }
}
