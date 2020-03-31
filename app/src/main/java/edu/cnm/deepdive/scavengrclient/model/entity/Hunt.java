package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity(
    indices = {
        @Index(value = {"hunt_name"})
    }
)
public class Hunt {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "local_hunt_id")
  private long localId;

  @NonNull
  @Expose
  @ColumnInfo(name = "hunt_id")
  private UUID id;

  @NonNull
  @Expose
  @ColumnInfo(name = "hunt_name", defaultValue = "New Hunt", collate = ColumnInfo.NOCASE)
  private String huntName;

  @Ignore
  @Expose
  private Organizer organizer;

  @NonNull
  @ColumnInfo(name = "organizer_name", collate = ColumnInfo.NOCASE)
  private String organizerName;

  @Ignore
  @Expose
  private List<Clue> clues = new LinkedList<>();

  public long getLocalId() {
    return localId;
  }

  public void setLocalId(long localId) {
    this.localId = localId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @NonNull
  public String getHuntName() {
    return huntName;
  }

  public void setHuntName(@NonNull String huntName) {
    this.huntName = huntName;
  }

  public Organizer getOrganizer() {
    return organizer;
  }

  public void setOrganizer(Organizer organizer) {
    this.organizer = organizer;
  }

  public String getOrganizerName() {
    return organizerName;
  }

  public void setOrganizerName(String organizerName) {
    this.organizerName = organizerName;
  }

  public List<Clue> getClues() {
    return clues;
  }

  public void setClues(List<Clue> clues) {
    this.clues = clues;
  }

  @NonNull
  @Override
  public String toString() {
    return (huntName + "by: " + organizerName);
  }
}
