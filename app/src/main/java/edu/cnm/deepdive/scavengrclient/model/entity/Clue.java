package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.UUID;

@Entity(
    indices = {
        @Index(value = {"hunt_order", "hunt_id"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = Hunt.class,
            parentColumns = "local_hunt_id",
            childColumns = "local_hunt_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    }
)
public class Clue {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "local_clue_id")
  private long localId;

  // server will ignore localId
  // TODO all entity fields that need to reach the server need @Expose!
  // TODO change all PrimaryKeys to "long localId"
  @ColumnInfo(name = "local_hunt_id", index = true)
  private long localHuntId;

  @NonNull
  @Expose
  @ColumnInfo(name = "clue_id")
  private UUID id;

  @NonNull
  @Expose
  @ColumnInfo(name = "clue_name", collate = ColumnInfo.NOCASE)
  private String clueName;

  @NonNull
  @Expose
  @ColumnInfo(name = "hunt_id", index = true)
  private String huntId;

  @NonNull
  @Expose
  private String media;

  @NonNull
  @ColumnInfo(name = "media_tag") // value contained in the QR code/NFC tag
  @Expose
  private String mediaTag;

  // Nullable for non-sequential hunts
  @ColumnInfo(name = "hunt_order")
  @Expose
  private Integer huntOrder;

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

  public long getLocalHuntId() {
    return localHuntId;
  }

  public void setLocalHuntId(long localHuntId) {
    this.localHuntId = localHuntId;
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

  @NonNull
  @Override
  public String toString() {
    return clueName;
  }
}
