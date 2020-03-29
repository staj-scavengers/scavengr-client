package edu.cnm.deepdive.scavengrclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {


  public MainViewModel(@NonNull Application application) {
    super(application);
  }
}
