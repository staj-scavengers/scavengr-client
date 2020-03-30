package edu.cnm.deepdive.scavengrclient.model.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nonnull;

@Entity(
    indices = {
        @Index(value = "date_started"),
        @Index(value = "date_completed"),
        @Index(value = "clues_completed")
    }
)
public class HuntActivity {

  @ColumnInfo(name = "hunt_activity_id")
  @PrimaryKey()
  private UUID id;

  @Nonnull
  private UUID huntId;

  @Nonnull
  private UUID userId;

  @ColumnInfo(name = "date_started")
  private Date started;

  @ColumnInfo(name = "date_completed")
  private Date completed;

  private Integer cluesCompleted;


  public UUID getId() {
    return id;
  }


  @Nonnull
  public UUID getHuntId() {
    return huntId;
  }

  public void setHuntId(@Nonnull UUID huntId) {
    this.huntId = huntId;
  }

  @Nonnull
  public UUID getUserId() {
    return userId;
  }

  public void setUserId(@Nonnull UUID userId) {
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
