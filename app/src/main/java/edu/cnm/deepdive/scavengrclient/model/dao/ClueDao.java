package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface ClueDao {

  @Insert
  Single<Long> insert(Clue clue);

  @Insert
  List<Long> insert(Collection<Clue> clues);

  @Update
  int update(Clue clue);

  @Delete
  int delete(Clue... clues);

  @Query("SELECT * FROM Clue WHERE clue_id = :id")
  Single<Clue> getOne(String id);

  @Query("SELECT * FROM Clue WHERE hunt_id = :id")
  LiveData<List<Clue>> getByHunt(String id);

  @Query("SELECT * FROM Clue")
  LiveData<List<Clue>> list();

}
