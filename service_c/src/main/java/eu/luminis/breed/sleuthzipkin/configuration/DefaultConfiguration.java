package eu.luminis.breed.sleuthzipkin.configuration;

import brave.http.HttpTracing;
import eu.luminis.breed.sleuth.TraceWebServiceClientInterceptor;
import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration.ServicesConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {

  @Bean
  public TraceWebServiceClientInterceptor traceWebServiceInterceptor(HttpTracing tracing) {
    return new TraceWebServiceClientInterceptor(tracing);
  }

  @Bean
  public ServicesConfiguration servicesConfiguration(
      @Value("${services.baseurl}") String baseUrl,
      @Value("${services.soapservice.port}") Integer portSoapService,
      @Value("${services.soapservice.url}") String urlSoapService){
    return ServicesConfigurationBuilder
        .aServicesConfiguration()
        .withBaseUrl(baseUrl)
        .withServiceConfigurationSoapService(new ServiceConfiguration(portSoapService, urlSoapService))
        .build();
  }
}
