package edu.cnm.deepdive.scavengrclient.model.entity;

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

  @ColumnInfo(name = "organizer_id")
  @PrimaryKey
  private UUID id;

  @Nonnull
  private UUID userId;

  @Ignore
  private Set<Hunt> hunts = new LinkedHashSet<>();

  public UUID getId() {
    return id;
  }

  @Nonnull
  public UUID getUserId() {
    return userId;
  }

  public void setUserId(@Nonnull UUID userId) {
    this.userId = userId;
  }

  public Set<Hunt> getHunts() {
    return hunts;
  }

  public void setHunts(Set<Hunt> hunts) {
    this.hunts = hunts;
  }
}
