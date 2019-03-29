package eu.luminis.breed.sleuthzipkin.model;

public class MachineInformation {
  private String power;
  private int temperature;

  public MachineInformation() {
  }

  public MachineInformation(String power, int temperature) {
    this.power = power;
    this.temperature = temperature;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int count) {
    this.temperature = count;
  }
}
