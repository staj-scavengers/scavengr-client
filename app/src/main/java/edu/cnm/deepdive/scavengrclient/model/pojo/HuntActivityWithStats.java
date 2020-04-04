package edu.cnm.deepdive.scavengrclient.model.pojo;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.room.Embedded;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import java.util.Date;

public class HuntActivityWithStats {

  @NonNull
  @Embedded
  private HuntActivity huntActivity;

  private Hunt hunt;

  private User user;

  private String organizerName;

  private Date started;

  private Date completed;

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

  public String getOrganizerName() {
    return organizerName;
  }

  public void setOrganizerName(String organizerName) {
    this.organizerName = organizerName;
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

  public String getTotalTime() {
    if(started == null || completed == null) {
      return null;
    }
    long minTotal = started.getTime() - completed.getTime();
    long seconds = minTotal / 1000;
    long minutes = seconds / 60;
    long hours = minutes / 60;
    long days = hours / 24;

    String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;


    return time;
  }


  public Integer getCluesCompleted() {
    return cluesCompleted;
  }

  public void setCluesCompleted(Integer cluesCompleted) {
    this.cluesCompleted = cluesCompleted;
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format("User: %s | Hunt: %s.  Clues Completed: %s of %s. Started: %tD | Completed: %tD",
        user.getUserName(), hunt.getHuntName(), cluesCompleted, hunt.getClues().size(), started, completed);
  }
}
