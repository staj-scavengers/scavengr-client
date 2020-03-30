package edu.cnm.deepdive.scavengrclient.model.entity;

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

  @ColumnInfo(name = "clue_id")
  @PrimaryKey
  private UUID id;

  @Nonnull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String clueName;

  @Nonnull
  private UUID huntId;

  @Nonnull
  private String media;

  @Nonnull
  private String mediaTag;

  // Nullable for non-sequential hunts
  private Integer huntOrder;

  public UUID getId() {
    return id;
  }

  @Nonnull
  public String getClueName() {
    return clueName;
  }

  public void setClueName(@Nonnull String clueName) {
    this.clueName = clueName;
  }

  @Nonnull
  public UUID getHuntId() {
    return huntId;
  }

  @Nonnull
  public String getMedia() {
    return media;
  }

  public void setMedia(@Nonnull String media) {
    this.media = media;
  }

  @Nonnull
  public String getMediaTag() {
    return mediaTag;
  }

  public void setMediaTag(@Nonnull String mediaTag) {
    this.mediaTag = mediaTag;
  }

  public Integer getHuntOrder() {
    return huntOrder;
  }

  public void setHuntOrder(Integer huntOrder) {
    this.huntOrder = huntOrder;
  }
}
