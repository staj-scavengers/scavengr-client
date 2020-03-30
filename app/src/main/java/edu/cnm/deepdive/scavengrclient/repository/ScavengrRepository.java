package edu.cnm.deepdive.scavengrclient.repository;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase;
import edu.cnm.deepdive.scavengrclient.service.ScavengrService;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScavengrRepository implements SharedPreferences.OnSharedPreferenceChangeListener {

  private static final int NETWORK_THREAD_COUNT = 10;

  private static Application context;

  private final ScavengrDatabase database;
  private final ScavengrService scavengr;
  private final Executor networkPool;
  private final SharedPreferences preferences;

  public ScavengrRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = ScavengrDatabase.getInstance();
    scavengr = ScavengrService.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.registerOnSharedPreferenceChangeListener(this);
  }

  public static void setContext(Application context) {
    ScavengrRepository.context = context;
  }

  public static ScavengrRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public Single<Hunt> downloadHunt(String token, UUID huntId) {
    return scavengr.getHunt(token, huntId)
        .subscribeOn(Schedulers.from(networkPool));
//        .doOnSuccess( save hunt locally );
  }

  public Single<Hunt> uploadHunt(String token, Hunt hunt) {
    return scavengr.postHunt(token, hunt).subscribeOn(Schedulers.from(networkPool));
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }


  private static class InstanceHolder {

    private static final ScavengrRepository INSTANCE = new ScavengrRepository();

  }
}
