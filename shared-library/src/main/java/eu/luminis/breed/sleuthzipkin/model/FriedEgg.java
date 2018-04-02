package eu.luminis.breed.sleuthzipkin.model;

public class FriedEgg {

  private String temperature;
  private String gas;
  private String preference;

  public FriedEgg(String temperature, String gas, String preference) {
    this.temperature = temperature;
    this.gas = gas;
    this.preference = preference;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getPreference() {
    return preference;
  }

  public void setPreference(String preference) {
    this.preference = preference;
  }

  public String getGas() {
    return gas;
  }

  public void setGas(String gas) {
    this.gas = gas;
  }
}
