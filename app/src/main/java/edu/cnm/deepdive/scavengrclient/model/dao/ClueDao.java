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
  Single<List<Long>> insert(Collection<Clue> clues);


  // TODO change other update & deletes to Singles
  @Update
  Single<Integer> update(Clue clue);

  @Delete
  Single<Integer> delete(Clue... clues);

  @Query("SELECT * FROM Clue WHERE local_clue_id = :id")
  Single<Clue> getOne(long id);

  @Query("SELECT * FROM Clue WHERE local_hunt_id = :id")
  LiveData<List<Clue>> getByHunt(long id);

  @Query("SELECT * FROM Clue")
  LiveData<List<Clue>> list();

}
