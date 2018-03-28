package eu.luminis.breed.sleuthzipkin.configuration;

public class ServiceConfiguration {

  public ServiceConfiguration(Integer port, String url) {
    this.port = port;
    this.url = url;
  }

  private Integer port;
  private String url;

  public int getPort() {
    return port != null ? port : 8080;
  }

  public String getUrl() {
    return url != null ? url : "";
  }
}
