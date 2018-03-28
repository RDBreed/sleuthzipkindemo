package eu.luminis.breed.sleuthzipkin.model;

public class FriedEgg {

  private String temperature;
  private String countryOfEgg;

  public FriedEgg() {
  }

  public FriedEgg(String temperature, String countryOfEgg) {
    this.temperature = temperature;
    this.countryOfEgg = countryOfEgg;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getCountryOfEgg() {
    return countryOfEgg;
  }

  public void setCountryOfEgg(String countryOfEgg) {
    this.countryOfEgg = countryOfEgg;
  }
}
