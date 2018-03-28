package eu.luminis.breed.sleuthzipkin.model;

public class ToasterInformation {
  private String power;
  private int count;

  public ToasterInformation() {
  }

  public ToasterInformation(String power, int count) {
    this.power = power;
    this.count = count;
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
}
