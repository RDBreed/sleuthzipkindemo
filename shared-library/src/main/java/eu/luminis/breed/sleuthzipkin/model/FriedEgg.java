package eu.luminis.breed.sleuthzipkin.model;

public class FriedEgg {

  private String temperature;
  private String preference;

  public FriedEgg() {
  }

  public FriedEgg(String temperature, String preference) {
    this.temperature = temperature;
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
}
