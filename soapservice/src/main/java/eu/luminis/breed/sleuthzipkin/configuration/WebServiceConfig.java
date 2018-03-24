package eu.luminis.breed.sleuthzipkin.configuration;

import brave.Tracer;
import brave.http.HttpTracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  private final Tracer tracer;
  private final HttpTracing sleuthHttpTracing;

  @Autowired
  public WebServiceConfig(Tracer tracer, HttpTracing sleuthHttpTracing) {
    this.tracer = tracer;
    this.sleuthHttpTracing = sleuthHttpTracing;
  }

  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet();
    dispatcherServlet.setApplicationContext(applicationContext);
    dispatcherServlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(dispatcherServlet, "/ws/*");
  }

  @Bean(name = "countries")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("CountriesPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace("sleuthzipkin.breed.luminis.eu");
    wsdl11Definition.setSchema(countriesSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema countriesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("service.xsd"));
  }
}
