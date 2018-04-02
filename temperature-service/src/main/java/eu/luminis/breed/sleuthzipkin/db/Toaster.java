//package eu.luminis.breed.sleuthzipkin.db;
//
//import java.util.UUID;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//
//@Entity
//public class Toaster {
//  @Id
//  @GeneratedValue
//  @Column(name="rating_id")
//  private UUID ratingId;
//  private int totalCount;
//
//  public Toaster(int totalCount) {
//    this.totalCount = totalCount;
//  }
//
//  public Toaster() {
//  }
//
//  public UUID getRatingId() {
//    return ratingId;
//  }
//
//  public void setRatingId(UUID ratingId) {
//    this.ratingId = ratingId;
//  }
//
//  public void addCount(){
//    this.totalCount++;
//  }
//
//  public int getTotalCount() {
//    return totalCount;
//  }
//
//  public void setTotalCount(int totalCount) {
//    this.totalCount = totalCount;
//  }
//}
