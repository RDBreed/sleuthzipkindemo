package eu.luminis.breed.sleuthzipkin.model;

public class Coffee {

  private String temperature;
  private String power;
  private String preference;

  public Coffee(String temperature, String power, String preference) {
    this.temperature = temperature;
    this.power = power;
    this.preference = preference;
  }

  public Coffee() {
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

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public void setPreference(String preference) {
    this.preference = preference;
  }
}
