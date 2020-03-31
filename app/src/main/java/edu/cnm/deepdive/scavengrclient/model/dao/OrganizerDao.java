package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.Organizer;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

// TODO Organizer is no longer a database entity.  Do we need this Dao?
@Dao
public interface OrganizerDao {

  @Insert
  Single<Long> insert(Organizer organizer);

  @Insert
  List<Long> insert(Collection<Organizer> organizers);

  @Update
  Single<Integer> update(Organizer organizer);

  @Delete
  Single<Integer> delete(Organizer... organizers);

//  @Query("SELECT * FROM Organizer")
//  LiveData<List<Organizer>> list();
//
//  @Query("SELECT * FROM Organizer WHERE organizer_id = :id")
//  Single<Organizer> select(String id);
}
