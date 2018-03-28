package eu.luminis.breed.sleuthzipkin.model;

public class ToastedBread {

  private String temperature;
  private String power;
  private int count;
  private String preferedDoneness;

  public ToastedBread() {
  }

  public ToastedBread(String temperature, String power, int count, String preferedDoneness) {
    this.temperature = temperature;
    this.power = power;
    this.count = count;
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

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getPreferedDoneness() {
    return preferedDoneness;
  }

  public void setPreferedDoneness(String preferedDoneness) {
    this.preferedDoneness = preferedDoneness;
  }
}
