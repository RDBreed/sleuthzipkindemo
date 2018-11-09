package eu.luminis.breed.sleuthzipkin.configuration;

import brave.http.HttpTracing;
import eu.luminis.breed.sleuth.TraceWebServiceClientInterceptor;
import eu.luminis.breed.sleuthzipkin.configuration.ServicesConfiguration.ServicesConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DefaultConfiguration {

  /**
   * Ensure that the resttemplate(s) is/are available as bean for autoconfiguration in {@link org.springframework.cloud.sleuth.instrument.web.client.TraceWebClientAutoConfiguration}, so that it is
   * configured to pass trace to next service(s) that are being called
   */
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public TraceWebServiceClientInterceptor traceWebServiceInterceptor(ApplicationContext applicationContext) {
    HttpTracing httpTracing;
    //If present, this means Sleuth is available. If not, create a mock, for our demo purpose.
    if(applicationContext.containsBean("httpTracing")){
      httpTracing = (HttpTracing) applicationContext.getBean("httpTracing");
    } else {
      httpTracing = HttpTracingMockFactory.createHttpTracingMock();
    }
    return new TraceWebServiceClientInterceptor(httpTracing);
  }

  @Bean
  public ServicesConfiguration servicesConfiguration(
      @Value("${services.baseurl}") String baseUrl,
      @Value("${services.d.port}") Integer portD,
      @Value("${services.d.url}") String urlD,
      @Value("${services.soapservice.port}") Integer portSoapService,
      @Value("${services.soapservice.url}") String urlSoapService) {
    return ServicesConfigurationBuilder
        .aServicesConfiguration()
        .withBaseUrl(baseUrl)
        .withTemperatureServiceConfiguration(new ServiceConfiguration(portD, urlD))
        .withEnergyServiceConfiguration(new ServiceConfiguration(portSoapService, urlSoapService))
        .build();
  }
}
