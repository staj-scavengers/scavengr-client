package edu.cnm.deepdive.scavengrclient.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;

public class HuntWithDetails {

  @NonNull
  @Embedded
  private Hunt hunt;

  private String huntName;

  private String organizerName;

  private int clueCount;

  @NonNull
  public Hunt getHunt() {
    return hunt;
  }

  public void setHunt(@NonNull Hunt hunt) {
    this.hunt = hunt;
  }

  public String getHuntName() {
    return huntName;
  }

  public void setHuntName(String huntName) {
    this.huntName = huntName;
  }

  public String getOrganizerName() {
    return organizerName;
  }

  public void setOrganizerName(String organizerName) {
    this.organizerName = organizerName;
  }

  public int getClueCount() {
    return clueCount;
  }

  public void setClueCount(int clueCount) {
    this.clueCount = clueCount;
  }
}
