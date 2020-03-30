package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;

@Entity(
    indices = {
        @Index(value = {"hunt_name"})
    }
)
public class Hunt {

  @NonNull
  @ColumnInfo(name = "hunt_id")
  @PrimaryKey()
  private String id = (UUID.randomUUID().toString());

  @NonNull
  @ColumnInfo(name = "hunt_name", defaultValue = "New Hunt", collate = ColumnInfo.NOCASE)
  private String huntName;

  @NonNull
  @ColumnInfo(name = "organizer_id")
  private String organizerId; // organizer name might be more useful for the client

  @Ignore
  private List<Clue> clues = new LinkedList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @NonNull
  public String getHuntName() {
    return huntName;
  }

  public void setHuntName(@NonNull String huntName) {
    this.huntName = huntName;
  }

  @NonNull
  public String getOrganizerId() {
    return organizerId;
  }

  public void setOrganizerId(@NonNull String organizerId) {
    this.organizerId = organizerId;
  }

  public List<Clue> getClues() {
    return clues;
  }

  public void setClues(List<Clue> clues) {
    this.clues = clues;
  }
}
