package edu.cnm.deepdive.scavengrclient.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.UUID;

@Entity(
    indices = {
        @Index(value = "date_started"),
        @Index(value = "date_completed"),
        @Index(value = "clues_completed")
    }
)
public class HuntActivity {

  @NonNull
  @ColumnInfo(name = "hunt_activity_id")
  @PrimaryKey()
  private String id = (UUID.randomUUID().toString());

  @NonNull
  @ColumnInfo(name = "hunt_id")
  private String huntId;

  @NonNull
  @ColumnInfo(name = "user_id")
  private String userId;

  @ColumnInfo(name = "date_started")
  private Date started;

  @ColumnInfo(name = "date_completed")
  private Date completed;

  @ColumnInfo(name = "clues_completed")
  private Integer cluesCompleted;

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @NonNull
  public String getHuntId() {
    return huntId;
  }

  public void setHuntId(@NonNull String huntId) {
    this.huntId = huntId;
  }

  @NonNull
  public String getUserId() {
    return userId;
  }

  public void setUserId(@NonNull String userId) {
    this.userId = userId;
  }

  public Date getStarted() {
    return started;
  }

  public void setStarted(Date started) {
    this.started = started;
  }

  public Date getCompleted() {
    return completed;
  }

  public void setCompleted(Date completed) {
    this.completed = completed;
  }

  public Integer getCluesCompleted() {
    return cluesCompleted;
  }

  public void setCluesCompleted(Integer cluesCompleted) {
    this.cluesCompleted = cluesCompleted;
  }
}
