package eu.luminis.breed.sleuthzipkin.model;

public class BModel {

  private String b;
  private String d;

  public BModel(String b, String d) {
    this.b = b;
    this.d = d;
  }

  public BModel() {
  }

  public void setB(String b) {
    this.b = b;
  }

  public void setD(String d) {
    this.d = d;
  }

  public String getB() {
    return b;
  }

  public String getD() {
    return d;
  }
}
