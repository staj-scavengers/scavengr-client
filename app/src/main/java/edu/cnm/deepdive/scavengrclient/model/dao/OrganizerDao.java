package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.Organizer;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface OrganizerDao {

  @Insert
  Single<Long> insert(Organizer organizer);

  @Insert
  List<Long> insert(Collection<Organizer> organizers);

  @Update
  int update(Organizer organizer);

  @Delete
  int delete(Organizer... organizers);

  @Query("SELECT * FROM Organizer")
  LiveData<List<Organizer>> list();

  @Query("SELECT * FROM Organizer WHERE organizer_id = :id")
  Single<Organizer> select(String id);
}
