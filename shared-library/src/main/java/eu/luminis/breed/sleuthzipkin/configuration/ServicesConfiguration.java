package eu.luminis.breed.sleuthzipkin.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;

public class ServicesConfiguration {

  private String baseUrl;
  private ServiceConfiguration serviceConfigurationB;
  private ServiceConfiguration serviceConfigurationC;
  private ServiceConfiguration serviceConfigurationD;
  private ServiceConfiguration serviceConfigurationSoapService;

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public void setServiceConfigurationB(ServiceConfiguration serviceConfigurationB) {
    this.serviceConfigurationB = serviceConfigurationB;
  }

  public void setServiceConfigurationC(ServiceConfiguration serviceConfigurationC) {
    this.serviceConfigurationC = serviceConfigurationC;
  }

  public void setServiceConfigurationD(ServiceConfiguration serviceConfigurationD) {
    this.serviceConfigurationD = serviceConfigurationD;
  }

  public void setServiceConfigurationSoapService(ServiceConfiguration serviceConfigurationSoapService) {
    this.serviceConfigurationSoapService = serviceConfigurationSoapService;
  }

  public URI getURIServiceB(String path)  {
    try {
      return new URIBuilder(getBaseUrlServiceB())
          .setPort(getPortServiceB())
          .setPath(path)
          .build();
    } catch (URISyntaxException e) {
      throw new GeneralException(e);
    }
  }

  public URI getURIServiceC(String path)  {
    try {
      return new URIBuilder(getBaseUrlServiceC())
          .setPort(getPortServiceC())
          .setPath(path)
          .build();
    } catch (URISyntaxException e) {
      throw new GeneralException(e);
    }
  }

  public URI getURIServiceD(String path) {
    try {
      return new URIBuilder(getBaseUrlServiceD())
          .setPort(getPortServiceD())
          .setPath(path)
          .build();
    } catch (URISyntaxException e) {
      throw new GeneralException(e);
    }
  }

  public URI getURIServiceSoapService(String path) {
    try {
      return new URIBuilder(getBaseUrlServiceSoapService())
          .setPort(getPortServiceSoapService())
          .setPath(path)
          .build();
    } catch (URISyntaxException e) {
      throw new GeneralException(e);
    }
  }

  public String getBaseUrlServiceB(){
    return baseUrl + serviceConfigurationB.getUrl();
  }

  public String getBaseUrlServiceC(){
    return baseUrl + serviceConfigurationC.getUrl();
  }

  public String getBaseUrlServiceD(){
    return baseUrl + serviceConfigurationD.getUrl();
  }

  public String getBaseUrlServiceSoapService(){
    return baseUrl + serviceConfigurationSoapService.getUrl();
  }

  public int getPortServiceB(){
    return serviceConfigurationB.getPort();
  }

  public int getPortServiceC(){
    return serviceConfigurationC.getPort();
  }

  public int getPortServiceD(){
    return serviceConfigurationD.getPort();
  }

  public int getPortServiceSoapService(){
    return serviceConfigurationSoapService.getPort();
  }

  public static final class ServicesConfigurationBuilder {

    private String baseUrl;
    private ServiceConfiguration serviceConfigurationB;
    private ServiceConfiguration serviceConfigurationC;
    private ServiceConfiguration serviceConfigurationD;
    private ServiceConfiguration serviceConfigurationSoapService;

    private ServicesConfigurationBuilder() {
    }

    public static ServicesConfigurationBuilder aServicesConfiguration() {
      return new ServicesConfigurationBuilder();
    }

    public ServicesConfigurationBuilder withBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public ServicesConfigurationBuilder withServiceConfigurationB(ServiceConfiguration serviceConfigurationB) {
      this.serviceConfigurationB = serviceConfigurationB;
      return this;
    }

    public ServicesConfigurationBuilder withServiceConfigurationC(ServiceConfiguration serviceConfigurationC) {
      this.serviceConfigurationC = serviceConfigurationC;
      return this;
    }

    public ServicesConfigurationBuilder withServiceConfigurationD(ServiceConfiguration serviceConfigurationD) {
      this.serviceConfigurationD = serviceConfigurationD;
      return this;
    }

    public ServicesConfigurationBuilder withServiceConfigurationSoapService(ServiceConfiguration serviceConfigurationSoapService) {
      this.serviceConfigurationSoapService = serviceConfigurationSoapService;
      return this;
    }

    public ServicesConfiguration build() {
      ServicesConfiguration servicesConfiguration = new ServicesConfiguration();
      servicesConfiguration.setBaseUrl(baseUrl);
      servicesConfiguration.setServiceConfigurationB(serviceConfigurationB);
      servicesConfiguration.setServiceConfigurationC(serviceConfigurationC);
      servicesConfiguration.setServiceConfigurationD(serviceConfigurationD);
      servicesConfiguration.setServiceConfigurationSoapService(serviceConfigurationSoapService);
      return servicesConfiguration;
    }
  }

}
