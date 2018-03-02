package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import brave.sampler.Sampler;
import org.springframework.cloud.sleuth.instrument.web.TraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class DefaultConfiguration {

  /**
   * Ensure that the resttemplate(s) is/are available as bean for autoconfiguration in {@link org.springframework.cloud.sleuth.instrument.web.client.TraceWebClientAutoConfiguration},
   * so that it is configured to pass trace to next service(s) that are being called
   */
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  /**
   * Define a sampler. Default is NEVER, but for this demonstration we use ALWAYS.
   * This will export all spans to zipkin. See https://cloud.spring.io/spring-cloud-sleuth/multi/multi__sampling.html
   */
  @Bean//
  public Sampler defaultSampler() {
    return Sampler.ALWAYS_SAMPLE;
  }


  /**
   * Our custom trace filter which will ensure a conversation id can be passed along.
   * We load this filter after the Tracefilter (which is the filter which actually starts/continues/handles our traces & spans.
   */
  @Bean
  @Order(TraceFilter.ORDER + 1)
  public GenericFilterBean customTraceFilter(Tracer tracer) {
    return new CustomTraceFilter(tracer);
  }
}
