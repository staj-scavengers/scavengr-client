package edu.cnm.deepdive.scavengrclient.model.pojo;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.room.Embedded;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import java.util.Date;

// TODO how is totalTime displayed?
public class HuntActivityWithStats {

  @NonNull
  @Embedded
  private HuntActivity huntActivity;

  private Hunt hunt;

  private User user;

  private Date started;

  private Date completed;

  private Long totalTime;

  private Integer cluesCompleted;

  @NonNull
  public HuntActivity getHuntActivity() {
    return huntActivity;
  }

  public void setHuntActivity(@NonNull HuntActivity huntActivity) {
    this.huntActivity = huntActivity;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Hunt getHunt() {
    return hunt;
  }

  public void setHunt(Hunt hunt) {
    this.hunt = hunt;
  }

  public Date getStarted() {
    return started;
  }

  public void setStarted(Date started) {
    this.started = started;
  }

  public Date getCompleted() {
    return completed;
  }

  public void setCompleted(Date completed) {
    this.completed = completed;
  }

  public Long getTotalTime() {
    if(started == null || completed == null) {
      return null;
    }
    return started.getTime() - completed.getTime();
  }

  public void setTotalTime(Long totalTime) {
    this.totalTime = totalTime;
  }

  public Integer getCluesCompleted() {
    return cluesCompleted;
  }

  public void setCluesCompleted(Integer cluesCompleted) {
    this.cluesCompleted = cluesCompleted;
  }

  // TODO Format dates better, calculate total completion time.
  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format("User: %s | Hunt: %s.  Clues Completed: %s of %s. Started: %tD | Completed: %tD",
        user.getUserName(), hunt.getHuntName(), cluesCompleted, hunt.getClues().size(), started, completed);
  }
}
