package eu.luminis.breed.sleuthzipkin.configuration;

import org.springframework.context.annotation.Configuration;

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
}
