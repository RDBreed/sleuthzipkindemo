package eu.luminis.breed.sleuthzipkin.configuration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import brave.Span;
import brave.Span.Kind;
import brave.Tracer;
import brave.Tracing;
import brave.http.HttpTracing;
import brave.propagation.Propagation;
import brave.propagation.Propagation.Getter;
import brave.propagation.Propagation.Setter;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;

/**
 * Factory for Mock bean of HttpTracing.
 *
 */
public class HttpTracingMockFactory {

  private HttpTracingMockFactory() {
  }

  /**
   * Mock bean for httptracing.
   * This ensures that without the Sleuth dependency all custom tracing for
   * SOAP requests are correctly handled, without deleting the interceptors.
   * Note: This is for demo purposes only!
   *
   * @return mocked HttpTracing
   */
  public static HttpTracing createHttpTracingMock(){
    HttpTracing httpTracing = mock(HttpTracing.class);
    Tracing tracing = mock(Tracing.class);
    when(httpTracing.tracing()).thenReturn(tracing);
    Tracer tracer = mock(Tracer.class);
    when(tracing.tracer()).thenReturn(tracer);
    Span span = mock(Span.class);
    when(tracer.nextSpan()).thenReturn(span);
    when(tracer.currentSpan()).thenReturn(span);
    when(span.name(anyString())).thenReturn(span);
    when(span.kind(any(Kind.class))).thenReturn(span);
    when(span.tag(anyString(), anyString())).thenReturn(span);
    Propagation propagation = mock(Propagation.class);
    when(tracing.propagation()).thenReturn(propagation);
    when(propagation.injector(any(Setter.class))).thenReturn(mock(Injector.class));
    when(propagation.extractor(any(Getter.class))).thenReturn(mock(Extractor.class));
    return httpTracing;
  }

}
