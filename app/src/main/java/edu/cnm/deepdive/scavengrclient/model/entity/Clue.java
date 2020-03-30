package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.UUID;
import javax.annotation.Nonnull;

@Entity(
    indices = {
        @Index(value = {"hunt_order", "hunt_id"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = Hunt.class,
            parentColumns = "hunt_id",
            childColumns = "hunt_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
}
)
public class Clue {

  @NonNull
  @ColumnInfo(name = "clue_id")
  @PrimaryKey()
  private String id = (UUID.randomUUID().toString());

  @NonNull
  @ColumnInfo(name = "clue_name", collate = ColumnInfo.NOCASE)
  private String clueName;

  @NonNull
  @ColumnInfo(name = "hunt_id", index = true)
  private String huntId;

  @NonNull
  private String media;

  @NonNull
  @ColumnInfo(name = "media_tag")
  private String mediaTag;

  // Nullable for non-sequential hunts
  @ColumnInfo(name = "hunt_order")
  private Integer huntOrder;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @NonNull
  public String getClueName() {
    return clueName;
  }

  public void setClueName(@NonNull String clueName) {
    this.clueName = clueName;
  }

  public void setHuntId(@NonNull String huntId) {
    this.huntId = huntId;
  }

  @NonNull
  public String getHuntId() {
    return huntId;
  }

  @NonNull
  public String getMedia() {
    return media;
  }

  public void setMedia(@NonNull String media) {
    this.media = media;
  }

  @NonNull
  public String getMediaTag() {
    return mediaTag;
  }

  public void setMediaTag(@NonNull String mediaTag) {
    this.mediaTag = mediaTag;
  }

  public Integer getHuntOrder() {
    return huntOrder;
  }

  public void setHuntOrder(Integer huntOrder) {
    this.huntOrder = huntOrder;
  }
}
