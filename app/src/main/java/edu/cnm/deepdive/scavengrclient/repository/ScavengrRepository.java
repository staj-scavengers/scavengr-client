package edu.cnm.deepdive.scavengrclient.repository;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase;
import edu.cnm.deepdive.scavengrclient.service.ScavengrService;
import edu.cnm.deepdive.scavengrclient.repository.ScavengrRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScavengrRepository implements SharedPreferences.OnSharedPreferenceChangeListener {

  private static final int NETWORK_THREAD_COUNT = 10;

  private static Application context;
  private final ScavengrDatabase database;
  private final Executor networkPool;
  private final ScavengrService scavengr;
  private final SharedPreferences preferences;

  public static void setContext(Application context) {
    ScavengrRepository.context = context;
  }

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

  public static ScavengrRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  //region server operations

  public Single<List<Hunt>> searchHunts(String token, String search) {
    return scavengr.searchHuntsByName(token, search)
        .subscribeOn(Schedulers.from(networkPool));
  }

  /**
   * This method chains multiple operations to add a {@link Hunt} record to the local database:
   *
   * 1. A Hunt is requested by {@link ScavengrService}.
   * 2. The name of the {@link User} who created the hunt is added to the Hunt object.
   * 3. The Hunt is saved to the {@link ScavengrDatabase}, which generates a local {@link Long} id
   *    that replaces the server's {@link UUID} as the Primary Key.
   * 4. That id is attached to the Hunt and each of its {@link Clue}s.
   * 5. The list of Clues is saved to the Database, and each has its unique local Long id attached.
   *
   * @param token from Oauth service, used to check User privileges
   * @param huntId identifies the Hunt to be downloaded.
   * @return the Hunt, modified from the server for the local database.
   */
  public Single<Hunt> downloadHunt(String token, UUID huntId) {
    return scavengr.getHunt(token, huntId)
        .subscribeOn(Schedulers.from(networkPool))
        .flatMap((Hunt hunt) -> { // add organizer name
              hunt.setOrganizerName(hunt.getOrganizer().getUser().getUserName());
              return database.getHuntDao().insert(hunt) // save hunt, get id
                  .map((id) -> {
                    hunt.setLocalId(id); // write new id from database into Clues
                    for (Clue clue : hunt.getClues()) {
                      clue.setLocalHuntId(id);
                    }
                    return hunt;
                  });
            }
        )
        .flatMap((hunt) -> // save all the clues
            database.getClueDao().insert(hunt.getClues())
                .subscribeOn(Schedulers.io())
                .map((ids) -> { // take the list of ids
                  Iterator<Long> idIterator = ids.iterator();
                  Iterator<Clue> clueIterator = hunt.getClues().iterator();
                  while (idIterator.hasNext()) { // add ids to clues in order
                    clueIterator.next().setLocalId(idIterator.next());
                  }
                  return hunt;
                })
        );
  }//        .subscribe in ViewModel will return a Hunt with Clues;

  public Single<Hunt> uploadHunt(String token, Hunt hunt) {
    return scavengr.postHunt(token, hunt).subscribeOn(Schedulers.from(networkPool));
  }
  //endregion

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }


  private static class InstanceHolder {

    private static final ScavengrRepository INSTANCE = new ScavengrRepository();

  }
}
