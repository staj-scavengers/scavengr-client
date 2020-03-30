package edu.cnm.deepdive.scavengrclient.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.UUID;
import javax.annotation.Nonnull;

@Entity(
    indices = {
        @Index(value = {"user_name"})
    }
)
public class User {

  @NonNull
  @ColumnInfo(name = "user_id")
  @PrimaryKey()
  private String id = (UUID.randomUUID().toString());

  @NonNull
  @ColumnInfo(name = "oauth_token")
  private String oauthToken;

  @NonNull
  @ColumnInfo(name = "user_name", collate = ColumnInfo.NOCASE)
  private String userName;

  @ColumnInfo(name = "organizer_id")
  private String organizerId;

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
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
}
