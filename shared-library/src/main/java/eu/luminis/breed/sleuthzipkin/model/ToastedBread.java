package eu.luminis.breed.sleuthzipkin.model;

public class ToastedBread {

  private String temperature;
  private String power;
  private String preferedDoneness;

  public ToastedBread() {
  }

  public ToastedBread(String temperature, String power, String preferedDoneness) {
    this.temperature = temperature;
    this.power = power;
    this.preferedDoneness = preferedDoneness;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String getPreferedDoneness() {
    return preferedDoneness;
  }

  public void setPreferedDoneness(String preferedDoneness) {
    this.preferedDoneness = preferedDoneness;
  }
}
