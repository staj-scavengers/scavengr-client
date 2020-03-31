package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface HuntActivityDao {

  @Insert
  Single<Long> insert(HuntActivity huntActivity);

  @Insert
  List<Long> insert(Collection<HuntActivity> huntActivities);

  @Update
  Single<Integer> update(HuntActivity huntActivity);

  @Delete
  Single<Integer> delete(HuntActivity... huntActivities);

  @Query("SELECT * FROM HuntActivity WHERE hunt_activity_id = :id")
  Single<HuntActivity> getOne(long id);

  @Query("SELECT * FROM HuntActivity WHERE hunt_id = :id")
  LiveData<List<Hunt>> getByHunt(String id);

  @Query("SELECT * FROM HuntActivity WHERE user_id = :id")
  LiveData<List<Hunt>> getByUser(String id);

  @Query("SELECT * FROM HuntActivity")
  LiveData<List<HuntActivity>> list();

}
