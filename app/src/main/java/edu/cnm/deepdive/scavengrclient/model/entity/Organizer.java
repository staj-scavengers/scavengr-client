package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Organizer is not an Entity on the client side of Scavengr.  {@link User} names are attached to {@link Hunt}s when hunts are downloaded
 */
public class Organizer {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "local_organizer_id")
  private long localId;

  @NonNull
  @Expose
  @ColumnInfo(name = "organizer_id")
  private UUID id;

  @NonNull
  @Expose
  private User user;

  @Ignore
  @Expose
  private Set<Hunt> hunts = new LinkedHashSet<>();

  public long getLocalId() {
    return localId;
  }

  public void setLocalId(long localId) {
    this.localId = localId;
  }

  @NonNull
  public UUID getId() {
    return id;
  }

  public void setId(@NonNull UUID id) {
    this.id = id;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  public Set<Hunt> getHunts() {
    return hunts;
  }

  public void setHunts(Set<Hunt> hunts) {
    this.hunts = hunts;
  }

  @NonNull
  @Override
  public String toString() {
    return (user.getUserName() + "(organizer)");
  }
}
