package edu.cnm.deepdive.scavengrclient;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.scavengrclient.repository.ScavengrRepository;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase;
import io.reactivex.schedulers.Schedulers;

public class ScavengerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);
    ScavengrRepository.setContext(this);
    ScavengrDatabase.setContext(this);
    ScavengrDatabase.getInstance().getHuntDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }


}
