package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }

)
public class Organizer {

  @NonNull
  @ColumnInfo(name = "organizer_id")
  @PrimaryKey()
  private String id = (UUID.randomUUID().toString());

  @NonNull
  @ColumnInfo(name = "user_id", index = true)
  private String userId;

  @Ignore
  private Set<Hunt> hunts = new LinkedHashSet<>();

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @NonNull
  public String getUserId() {
    return userId;
  }

  public void setUserId(@NonNull String userId) {
    this.userId = userId;
  }

  public Set<Hunt> getHunts() {
    return hunts;
  }

  public void setHunts(Set<Hunt> hunts) {
    this.hunts = hunts;
  }
}
