package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.UUID;

/**
 * User is the basic profile for anyone who uses the app
 */
@Entity(
    indices = {
        @Index(value = {"user_name"})
    }
)
public class User {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "local_user_id")
  private long localId;

  @Expose
  @ColumnInfo(name = "user_id")
  private UUID id;

  @NonNull
  @Expose
  @ColumnInfo(name = "oauth_token")
  private String oauthToken;

  @NonNull
  @Expose
  @ColumnInfo(name = "user_name", collate = ColumnInfo.NOCASE)
  private String userName;

  @Expose
  @ColumnInfo(name = "organizer_id")
  private String organizerId;

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
  public String getOauthToken() {
    return oauthToken;
  }

  public void setOauthToken(@NonNull String oauthToken) {
    this.oauthToken = oauthToken;
  }

  @NonNull
  public String getUserName() {
    return userName;
  }

  public void setUserName(@NonNull String userName) {
    this.userName = userName;
  }

  public String getOrganizerId() {
    return organizerId;
  }

  public void setOrganizerId(String organizerId) {
    this.organizerId = organizerId;
  }

  @NonNull
  @Override
  public String toString() {
    return userName;
  }
}
