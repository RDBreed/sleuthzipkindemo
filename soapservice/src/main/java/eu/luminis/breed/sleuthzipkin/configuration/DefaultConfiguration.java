package eu.luminis.breed.sleuthzipkin.configuration;

import eu.luminis.breed.sleuth.PreTracingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {

  @Bean
  public PreTracingFilter preTracingFilter() {
    return new PreTracingFilter();
  }
}
