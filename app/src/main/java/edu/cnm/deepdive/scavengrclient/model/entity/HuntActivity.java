package edu.cnm.deepdive.scavengrclient.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

@Entity(
    indices = {
        @Index(value = "date_started"),
        @Index(value = "date_completed"),
        @Index(value = "clues_completed")
    },
    foreignKeys = {
        @ForeignKey(
            entity = Hunt.class,
            parentColumns = "local_hunt_id",
            childColumns = "local_hunt_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = User.class,
            parentColumns = "local_user_id",
            childColumns = "local_user_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    }
)
public class HuntActivity {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "local_hunt_activity_id")
  private long localId;

  @NonNull
  @Expose
  @ColumnInfo(name = "hunt_activity_id")
  private UUID id;

  @NonNull
  @ColumnInfo(name = "local_hunt_id", index = true)
  private long huntLocalId;

  @NonNull
  @ColumnInfo(name = "local_user_id", index = true)
  private long userLocalId;

  @NonNull
  @Expose
  @ColumnInfo(name = "hunt_id")
  private String huntId;

  @NonNull
  @Expose
  @ColumnInfo(name = "user_id")
  private String userId;

  @Expose
  @ColumnInfo(name = "date_started")
  private Date started;

  @Expose
  @ColumnInfo(name = "date_completed")
  private Date completed;

  @Expose
  @ColumnInfo(name = "clues_completed")
  private Integer cluesCompleted;

  public long getLocalId() {
    return localId;
  }

  public void setLocalId(long localId) {
    this.localId = localId;
  }

  public long getHuntLocalId() {
    return huntLocalId;
  }

  public void setHuntLocalId(long huntLocalId) {
    this.huntLocalId = huntLocalId;
  }

  public long getUserLocalId() {
    return userLocalId;
  }

  public void setUserLocalId(long userLocalId) {
    this.userLocalId = userLocalId;
  }

  @NonNull
  public UUID getId() {
    return id;
  }

  public void setId(@NonNull UUID id) {
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

  @NonNull
  @Override
  public String toString() {
    return ("User Id: " + userLocalId + " | Hunt Id:" + huntLocalId);
  }
}
