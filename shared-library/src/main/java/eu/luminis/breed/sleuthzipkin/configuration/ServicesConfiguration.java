package eu.luminis.breed.sleuthzipkin.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;

public class ServicesConfiguration {

  private String baseUrl;
  private ServiceConfiguration machineServiceConfiguration;
  private ServiceConfiguration preferenceServiceConfiguration;
  private ServiceConfiguration temperatureServiceConfiguration;
  private ServiceConfiguration energyServiceConfiguration;

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public void setMachineServiceConfiguration(ServiceConfiguration machineServiceConfiguration) {
    this.machineServiceConfiguration = machineServiceConfiguration;
  }

  public void setPreferenceServiceConfiguration(ServiceConfiguration preferenceServiceConfiguration) {
    this.preferenceServiceConfiguration = preferenceServiceConfiguration;
  }

  public void setTemperatureServiceConfiguration(ServiceConfiguration temperatureServiceConfiguration) {
    this.temperatureServiceConfiguration = temperatureServiceConfiguration;
  }

  public void setEnergyServiceConfiguration(ServiceConfiguration energyServiceConfiguration) {
    this.energyServiceConfiguration = energyServiceConfiguration;
  }

  public URI getURIMachineService(String path) throws URISyntaxException {
    return new URIBuilder(getBaseUrlMachineService())
        .setPort(getPortMachineService())
        .setPath(path)
        .build();
  }

  public URI getURIPreferenceService(String path) throws URISyntaxException {
    return new URIBuilder(getBaseUrlPreferenceService())
        .setPort(getPortPreferenceService())
        .setPath(path)
        .build();
  }

  public URI getURITemperatureService(String path) throws URISyntaxException {
    return new URIBuilder(getBaseUrlTemperatureService())
        .setPort(getPortTemperatureService())
        .setPath(path)
        .build();
  }

  public URI getURIEnergyService(String path) throws URISyntaxException {
    return new URIBuilder(getBaseUrlEnergyService())
        .setPort(getPortEnergyService())
        .setPath(path)
        .build();
  }

  public String getBaseUrlMachineService(){
    return baseUrl + machineServiceConfiguration.getUrl();
  }

  public String getBaseUrlPreferenceService(){
    return baseUrl + preferenceServiceConfiguration.getUrl();
  }

  public String getBaseUrlTemperatureService(){
    return baseUrl + temperatureServiceConfiguration.getUrl();
  }

  public String getBaseUrlEnergyService(){
    return baseUrl + energyServiceConfiguration.getUrl();
  }

  public int getPortMachineService(){
    return machineServiceConfiguration.getPort();
  }

  public int getPortPreferenceService(){
    return preferenceServiceConfiguration.getPort();
  }

  public int getPortTemperatureService(){
    return temperatureServiceConfiguration.getPort();
  }

  public int getPortEnergyService(){
    return energyServiceConfiguration.getPort();
  }

  public static final class ServicesConfigurationBuilder {

    private String baseUrl;
    private ServiceConfiguration machineServiceConfiguration;
    private ServiceConfiguration preferenceServiceConfiguration;
    private ServiceConfiguration temperatureServiceConfiguration;
    private ServiceConfiguration energyServiceConfiguration;

    private ServicesConfigurationBuilder() {
    }

    public static ServicesConfigurationBuilder aServicesConfiguration() {
      return new ServicesConfigurationBuilder();
    }

    public ServicesConfigurationBuilder withBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public ServicesConfigurationBuilder withMachineServiceConfiguration(ServiceConfiguration machineServiceConfiguration) {
      this.machineServiceConfiguration = machineServiceConfiguration;
      return this;
    }

    public ServicesConfigurationBuilder withPreferenceServiceConfiguration(ServiceConfiguration preferenceServiceConfiguration) {
      this.preferenceServiceConfiguration = preferenceServiceConfiguration;
      return this;
    }

    public ServicesConfigurationBuilder withTemperatureServiceConfiguration(ServiceConfiguration temperatureServiceConfiguration) {
      this.temperatureServiceConfiguration = temperatureServiceConfiguration;
      return this;
    }

    public ServicesConfigurationBuilder withEnergyServiceConfiguration(ServiceConfiguration energyServiceConfiguration) {
      this.energyServiceConfiguration = energyServiceConfiguration;
      return this;
    }

    public ServicesConfiguration build() {
      ServicesConfiguration servicesConfiguration = new ServicesConfiguration();
      servicesConfiguration.setBaseUrl(baseUrl);
      servicesConfiguration.setMachineServiceConfiguration(machineServiceConfiguration);
      servicesConfiguration.setPreferenceServiceConfiguration(preferenceServiceConfiguration);
      servicesConfiguration.setTemperatureServiceConfiguration(temperatureServiceConfiguration);
      servicesConfiguration.setEnergyServiceConfiguration(energyServiceConfiguration);
      return servicesConfiguration;
    }
  }

}
