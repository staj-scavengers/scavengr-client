package edu.cnm.deepdive.scavengrclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import edu.cnm.deepdive.scavengrclient.model.pojo.HuntActivityWithStats;
import edu.cnm.deepdive.scavengrclient.repository.ScavengrRepository;
import edu.cnm.deepdive.scavengrclient.service.GoogleSignInService;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  public LiveData<List<HuntActivityWithStats>> getAllApodSummaries() {
    return repository.get();
  }

  private final MutableLiveData<Hunt> hunt;
  private final MutableLiveData<List<Hunt>> hunts;
  private final MutableLiveData<List<Clue>> clues;
  private final MutableLiveData<String> mediaTag;
  private final MutableLiveData<String> media;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final ScavengrRepository repository;

  public MainViewModel(@NonNull Application application) {
    super(application);
    repository = ScavengrRepository.getInstance();
    pending = new CompositeDisposable();
    throwable = new MutableLiveData<>();
    media = new MutableLiveData<>();
    mediaTag = new MutableLiveData<>();
    clues = new MutableLiveData<>();
    hunts = new MutableLiveData<>();
    hunt = new MutableLiveData<>();

  }

  public LiveData<Hunt> getHunt() {
    return hunt;
  }

  public LiveData<List<Clue>> getClues() {
    return clues;
  }

  public LiveData<String> getMediaTag() {
    return mediaTag;
  }

  public LiveData<String> getMedia() {
    return media;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void searchHunts(String search) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.searchHunts(account.getIdToken(), search)
                  .subscribe(
                      hunts::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }

  public void downloadHunt(UUID huntId) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.downloadHunt(account.getIdToken(), huntId)
                  .subscribe(
                      hunt::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }

  public Maybe<User> checkUser(String token) {
    return repository.checkLocalUser(token);
  }

  public void register(String name) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
              pending.add(
                  repository.registerUser(account.getIdToken(),
                      ((name.isEmpty() ? account.getDisplayName() : name)))
                      .subscribe()
              );
            }
        );
  }


}
