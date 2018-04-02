package eu.luminis.breed.sleuthzipkin.model;

public class Coffee {

  private final String temperature;
  private final String power;
  private final String preference;

  public Coffee(String temperature, String power, String preference) {
    this.temperature = temperature;
    this.power = power;
    this.preference = preference;
  }

  public String getTemperature() {
    return temperature;
  }

  public String getPower() {
    return power;
  }

  public String getPreference() {
    return preference;
  }
}
