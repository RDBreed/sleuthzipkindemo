package eu.luminis.breed.sleuthzipkin.configuration;

import eu.luminis.breed.sleuth.TraceWebServiceClientInterceptor;
import eu.luminis.breed.sleuthzipkin.soap.SoapServiceClient;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class WebServiceConfiguration {

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setPackagesToScan("eu.luminis.breed.sleuthzipkin");
    return marshaller;
  }

  @Bean
  public SoapServiceClient soapServiceClient(Jaxb2Marshaller marshaller, TraceWebServiceClientInterceptor traceWebServiceInterceptor, ServicesConfiguration servicesConfiguration)
      throws URISyntaxException {
    SoapServiceClient client = new SoapServiceClient();
    client.setDefaultUri(servicesConfiguration.getURIServiceSoapService("ws").toString());
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    client.setInterceptors(addTraceInterceptor(traceWebServiceInterceptor, client.getInterceptors()));
    return client;
  }

  private ClientInterceptor[] addTraceInterceptor(
      ClientInterceptor traceWebServiceInterceptor, ClientInterceptor[] interceptors) {
    List<ClientInterceptor> interceptorsList;
    if (interceptors == null) {
      interceptorsList = new ArrayList<>();
    } else {
      interceptorsList = new ArrayList<>(Arrays.asList(interceptors));
    }
    interceptorsList.add(traceWebServiceInterceptor);
    return interceptorsList.toArray(new ClientInterceptor[0]);
  }
}
