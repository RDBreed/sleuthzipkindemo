package eu.luminis.breed.sleuthzipkin.configuration;

import brave.http.HttpTracing;
import eu.luminis.breed.sleuth.PreTracingFilter;
import eu.luminis.breed.sleuth.TraceWebServiceServerInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfiguration {


  @Bean
  public TraceWebServiceServerInterceptor traceWebServiceServerInterceptor(ApplicationContext applicationContext) {
    HttpTracing httpTracing;
    //If present, this means Sleuth is available. If not, create a mock, for our demo purpose.
    if(applicationContext.containsBean("httpTracing")){
      httpTracing = (HttpTracing) applicationContext.getBean("httpTracing");
    } else {
      httpTracing = HttpTracingMockFactory.createHttpTracingMock();
    }
    return new TraceWebServiceServerInterceptor(httpTracing);
  }

  @Bean
  public PreTracingFilter preTracingFilter() {
    return new PreTracingFilter();
  }
}
