package edu.cnm.deepdive.scavengrclient.model.entity;

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

  @ColumnInfo(name = "user_id")
  @PrimaryKey
  private UUID id;

  @Nonnull
  private String oauthToken;

  @Nonnull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String userName;

  private Organizer organizer;

}
