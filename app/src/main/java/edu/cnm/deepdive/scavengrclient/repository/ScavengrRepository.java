package edu.cnm.deepdive.scavengrclient.repository;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.scavengrclient.ScavengerApplication;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import edu.cnm.deepdive.scavengrclient.service.ScavengrDatabase;
import edu.cnm.deepdive.scavengrclient.service.ScavengrService;
import edu.cnm.deepdive.scavengrclient.repository.ScavengrRepository;
import edu.cnm.deepdive.scavengrclient.viewmodel.MainViewModel;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The ScavengrRepository contains methods to interact with the {@link ScavengrService}, which
 * handles HTTP requests to the server, and the {@link ScavengrDatabase}, which handles local
 * entities.  It will also manage user settings in the client application.
 */
public class ScavengrRepository implements SharedPreferences.OnSharedPreferenceChangeListener {

  private static final int NETWORK_THREAD_COUNT = 10;

  private static Application context;
  private final ScavengrDatabase database;
  private final Executor networkPool;
  private final ScavengrService scavengr;
  private final SharedPreferences preferences;

  /**
   * This method receives an {@link Application} context when the Repository is initialized.
   *
   * @param context received when {@link ScavengerApplication} runs.
   */
  public static void setContext(Application context) {
    ScavengrRepository.context = context;
  }

  /**
   * The ScavengrRepository constructor initializes (or just receives) singleton Instances of the
   * {@link ScavengrDatabase} and {@link ScavengrRepository}, establishes a pool of {@link Thread}s
   * for network requests, and initializes the {@link SharedPreferences} interface.
   * <p>
   * The Repository constructor throws an {@link IllegalStateException} if the {@link Application}
   * context passed from the {@link ScavengerApplication} is null.
   */
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

  //region server operations

  /**
   * This method requests a list of {@link Hunt}s from the server with huntName fields that contain
   * the search {@link String}.
   *
   * @param token  is for user authentication
   * @param search is the String to search by
   * @return a {@link List}<{@link Hunt}> collection wrapped in a {@link Single} for the {@link MainViewModel}.
   */
  public Single<List<Hunt>> searchHunts(String token, String search) {
    return scavengr.searchHuntsByName(token, search)
        .subscribeOn(Schedulers.from(networkPool));
  }

  /**
   * This method chains multiple operations to add a {@link Hunt} record to the local database:
   * <p>
   * 1. A Hunt is requested by {@link ScavengrService}. 2. The name of the {@link User} who created
   * the hunt is added to the Hunt object. 3. The Hunt is saved to the {@link ScavengrDatabase},
   * which generates a local {@link Long} id that replaces the server's {@link UUID} as the Primary
   * Key. 4. That id is attached to the Hunt and each of its {@link Clue}s. 5. The list of Clues is
   * saved to the Database, and each has its unique local Long id attached.
   *
   * @param token  from Oauth service, used to check User privileges
   * @param huntId identifies the Hunt to be downloaded.
   * @return the Hunt, modified from the server for the local database and wrapped in a {@link Single} for the {@link MainViewModel}.
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

  /**
   * This method adds a new {@link Hunt} to the server.
   *
   * @param token is for user authentication.
   * @param hunt is the new Hunt to be added.
   * @return the new Hunt on success, wrapped in a {@link Single} for the {@link MainViewModel}
   */
  public Single<Hunt> uploadHunt(String token, Hunt hunt) {
    return scavengr.postHunt(token, hunt).subscribeOn(Schedulers.from(networkPool));
  }
  //endregion

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }

  /**
   * This method is part of the singleton pattern that ensures there is only one ScavengrRepository
   * instance in operation.
   *
   * @return the singleton Instance.
   */
  public static ScavengrRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * The InstanceHolder class is part of the singleton pattern that ensures there is only one
   * ScavengrRepository in operation.  It provides a static Instance of the Repository.
   */
  private static class InstanceHolder {

    private static final ScavengrRepository INSTANCE = new ScavengrRepository();

  }
}
