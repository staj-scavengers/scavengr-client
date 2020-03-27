package edu.cnm.deepdive.scavengrclient.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase.Converters;
import java.util.Date;


@Database(
    entities = {},
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters.class)
public abstract class ScavengrDatabase extends RoomDatabase{

  private static final String DB_NAME = "scavengr_db";

  private static Application context;

  public static void setContext(Application context) {
    ScavengrDatabase.context = context;
  }

  public static ScavengrDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

//  public abstract ScavengrDao getScavengrDao();

  private static class InstanceHolder {

    private static final ScavengrDatabase INSTANCE =
        Room.databaseBuilder(context, ScavengrDatabase.class, DB_NAME)
            .build();

  }

  public static class Converters {

    @TypeConverter
    public static Long fromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public static Date fromLong(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }
}
