package edu.cnm.deepdive.scavengrclient.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface UserDao {

  @Insert
  Single<Long> insert(User user);

  @Insert
  List<Long> insert(Collection<User> users);

  @Update
  Single<Integer> update(User user);

  @Delete
  Single<Integer> delete(User... users);

  @Query("SELECT * FROM User")
  LiveData<List<User>> list();

  @Query("SELECT * FROM User WHERE local_user_id = :id")
  Single<User> select(long id);
}
