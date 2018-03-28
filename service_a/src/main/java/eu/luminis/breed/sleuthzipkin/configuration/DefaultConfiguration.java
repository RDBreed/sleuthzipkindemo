package eu.luminis.breed.sleuthzipkin.configuration;

import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration.ServicesConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
