package edu.cnm.deepdive.scavengrclient;

import android.app.Application;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;

public class ScavengerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }


}
