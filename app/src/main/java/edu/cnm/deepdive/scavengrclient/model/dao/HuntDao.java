package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface HuntDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Hunt hunt);

  @Insert
  List<Long> insert(Collection<Hunt> hunts);

  @Update
  Single<Integer> update(Hunt hunt);

  @Delete
  Single<Integer> delete(Hunt... hunts);

  @Query("SELECT * FROM Hunt WHERE local_hunt_id = :id")
  Single<Hunt> getOne(long id);

  @Query("SELECT * FROM Hunt WHERE hunt_name LIKE :search")
  LiveData<List<Hunt>> searchByName(String search);

  @Query("SELECT * FROM Hunt")
  LiveData<List<Hunt>> list();

}
