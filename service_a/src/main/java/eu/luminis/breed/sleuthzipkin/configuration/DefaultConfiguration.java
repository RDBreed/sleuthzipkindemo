package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import brave.sampler.Sampler;
import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration.ServicesConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class DefaultConfiguration {

  /**
   * Ensure that the resttemplate(s) is/are available as bean(s) for autoconfiguration in {@link org.springframework.cloud.sleuth.instrument.web.client.TraceWebClientAutoConfiguration},
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
  @Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
  public GenericFilterBean customTraceFilter(Tracer tracer) {
    return new CustomTraceFilter(tracer);
  }

  @Bean
  public ServicesConfiguration servicesConfiguration(
      @Value("${services.baseurl}") String baseUrl,
      @Value("${services.b.port}") Integer portB,
      @Value("${services.b.url}") String urlB,
      @Value("${services.c.port}") Integer portC,
      @Value("${services.c.url}") String urlC,
      @Value("${services.d.port}") Integer portD,
      @Value("${services.d.url}") String urlD){
    return ServicesConfigurationBuilder
        .aServicesConfiguration()
        .withBaseUrl(baseUrl)
        .withServiceConfigurationB(new ServiceConfiguration(portB, urlB))
        .withServiceConfigurationC(new ServiceConfiguration(portC, urlC))
        .withServiceConfigurationD(new ServiceConfiguration(portD, urlD))
        .build();
  }
}
