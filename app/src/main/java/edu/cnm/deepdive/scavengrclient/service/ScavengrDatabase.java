package edu.cnm.deepdive.scavengrclient.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.scavengrclient.ScavengerApplication;
import edu.cnm.deepdive.scavengrclient.model.dao.ClueDao;
import edu.cnm.deepdive.scavengrclient.model.dao.HuntDao;
import edu.cnm.deepdive.scavengrclient.model.dao.UserDao;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Organizer;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase.Converters;
import java.util.Date;
import java.util.UUID;

/**
 * The client-side ScavengrDatabase holds {@link Hunt} details, which includes {@link Clue}s and
 * {@link HuntActivity} records.
 */
@Database(
    entities = {Clue.class, Hunt.class, HuntActivity.class, User.class},
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters.class)
public abstract class ScavengrDatabase extends RoomDatabase {

  private static final String DB_NAME = "scavengr_db";

  private static Application context;

  /**
   * This method receives an {@link Application} context when the Database is initialized.
   *
   * @param context received when {@link ScavengerApplication} runs.
   */
  public static void setContext(Application context) {
    ScavengrDatabase.context = context;
  }

  /**
   * Retrieves an Instance of the Data Access Object that handles queries to the local {@link Hunt}
   * database
   *
   * @return a {@link HuntDao} interface
   */
  public abstract HuntDao getHuntDao();

  /**
   * Retrieves an Instance of the Data Access Object that handles queries to the local {@link Clue}
   * database
   *
   * @return a {@link HuntDao} interface
   */
  public abstract ClueDao getClueDao();

  public abstract UserDao getUserDao();

  /**
   * This method is part of the singleton pattern that ensures there is only one ScavengrDatabase
   * instance in operation.
   *
   * @return the singleton Instance.
   */
  public static ScavengrDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * The InstanceHolder class is part of the singleton pattern that ensures there is only one
   * ScavengrDatabase in operation.  It provides a static Instance of the Database.
   */
  private static class InstanceHolder {

    private static final ScavengrDatabase INSTANCE =
        Room.databaseBuilder(context, ScavengrDatabase.class, DB_NAME)
            .build();

  }

  /**
   * The Converts class holds methods for {@link Long} value / {@link Date} object conversion and
   * server {@link UUID} / local {@link String} conversion
   */
  public static class Converters {

    /**
     * This method receives a {@link Date} and returns its value as a {@link Long}.
     * @param date to be converted.
     * @return Long conversion result.
     */
    @TypeConverter
    public static Long fromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }
    /**
     * This method receives a {@link Long} and returns its value as a {@link Date}.
     * @param value to be converted.
     * @return Date conversion result.
     */
    @TypeConverter
    public static Date fromLong(Long value) {
      return (value != null) ? new Date(value) : null;
    }

    /**
     * This method receives a {@link UUID} and returns its value as a {@link String}.
     * @param uuid to be converted.
     * @return String conversion result.
     */
    @TypeConverter
    public static String fromUUID(UUID uuid) {
      return (uuid != null) ? uuid.toString() : null;
    }

    /**
     * This method receives a {@link String} and returns its contents as a {@link UUID}.
     * @param string to be converted.
     * @return UUID conversion result.
     */
    @TypeConverter
    public static UUID fromString(String string) {
      return (string != null) ? UUID.fromString(string) : null;
    }

  }
}
