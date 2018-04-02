package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class DefaultSleuthAndZipkinConfiguration {
  /**
   * Define a sampler. Default is NEVER, but for this demonstration we use ALWAYS.
   * This will export all spans to zipkin. See https://cloud.spring.io/spring-cloud-sleuth/multi/multi__sampling.html
   */
//  @Bean
//  public Sampler defaultSampler() {
//    return Sampler.ALWAYS_SAMPLE;
//  }


  /**
   * Our custom trace filter which will ensure a conversation id can be passed along.
   * We load this filter after the Tracefilter (which is the filter which actually starts/continues/handles our traces & spans.
   */
  @Bean
  @Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
  public GenericFilterBean customTraceFilter(Tracer tracer) {
    return new CustomTraceFilter(tracer);
  }
}
