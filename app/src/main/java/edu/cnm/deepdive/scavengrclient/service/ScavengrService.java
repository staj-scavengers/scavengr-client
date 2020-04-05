package edu.cnm.deepdive.scavengrclient.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.scavengrclient.BuildConfig;
import edu.cnm.deepdive.scavengrclient.model.entity.Clue;
import edu.cnm.deepdive.scavengrclient.model.entity.Hunt;
import edu.cnm.deepdive.scavengrclient.model.entity.HuntActivity;
import edu.cnm.deepdive.scavengrclient.model.entity.Organizer;
import edu.cnm.deepdive.scavengrclient.model.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface handles communication with the Scavengr Server.
 */
public interface ScavengrService {

//region Hunt CRUD methods
  //Single Hunt Returns

  /**
   * This method retrieves a specific {@link Hunt}.
   *
   * @param oauthHeader for user authentication
   * @param id          is a Hunt's {@link UUID}
   * @return a Hunt if one has the matching id.
   */
  @GET("hunts/{id}")
  Single<Hunt> getHunt(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  /**
   * This method adds a new {@link Hunt} to the server.
   *
   * @param oauthHeader for user authentication
   * @param hunt        is the new Hunt.
   * @return the same Hunt object on success.
   */
  @POST("hunts/")
  Single<Hunt> postHunt(@Header("Authorization") String oauthHeader, @Body Hunt hunt);

  /**
   * This method updates an existing {@link Hunt}.
   *
   * @param oauthHeader for user authentication.
   * @param hunt        contains updated fields for the Hunt object.
   * @param id          identifies the specific Hunt to be updated.
   * @return the updated Hunt on success.
   */
  @PUT("hunts/{id}")
  Single<Hunt> putHunt(@Header("Authorization") String oauthHeader, @Body Hunt hunt,
      @Path("id") UUID id);

  /**
   * This method deletes a single {@link Hunt}.
   *
   * @param oauthHeader for user authentication.
   * @param id          identifies the Hunt to be deleted.
   * @return a {@link Completable} to confirm the deletion.
   */
  @DELETE("hunts/{id}")
  Completable deleteHunt(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  //Multiple Hunt Returns
  // TODO update these to return a smaller HuntWithStats POJO (huntId, huntName, organizer name, number of clues?)

  /**
   * This method {@link List}s all {@link Hunt}s created by one {@link Organizer}
   *
   * @param oauthHeader for user authentication.
   * @param organizer   is the Organizer whose hunts will be retrieved.
   * @return any Hunts that match
   */
  @GET("hunts/")
  Single<List<Hunt>> getHuntsByOrganizer(@Header("Authorization") String oauthHeader,
      @Query("organizer") Organizer organizer);

  //TODO Add Parameters to controller search method and check for null values.
  /**
   * This method {@link List}s all {@link Hunt}s based on a search of the huntName field.
   *
   * @param oauthHeader for user authentication.
   * @param huntName    is the search string.
   * @return any Hunts that match.
   */
  @GET("hunts/search")
  Single<List<Hunt>> searchHunts(@Header("Authorization") String oauthHeader,
      @Query("name") String huntName, @Query("isOpen") Boolean open, @Query("active") Boolean active);
//endregion

//region Clue CRUD methods
  //Single Clue Returns

  /**
   * This method returns a single {@link Clue} by id
   *
   * @param oauthHeader for user authentication.
   * @param id          is a Clue's {@link UUID}.
   * @return a Clue, if one has a matching id.
   */
  @GET("clues/{id}")
  Single<Clue> getClue(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  /**
   * This method adds a single {@link Clue} to the server.
   *
   * @param oauthHeader for user authentication.
   * @param clue        contains fields for the new Clue.
   * @return the same Clue on success.
   */
  @POST("clues/")
  Single<Clue> postClue(@Header("Authorization") String oauthHeader, @Body Clue clue);

  /**
   * This method updates an existing {@link Clue}.
   *
   * @param oauthHeader for user authentication.
   * @param id          identifies the Clue to be updated
   * @param clue        contains fields to be changed.  Accepts empty values.
   * @return the updated Clue on success.
   */
  @PUT("clues/{id}")
  Single<Clue> putClue(@Header("Authorization") String oauthHeader, @Path("id") UUID id,
      @Body Clue clue);

  /**
   * This method attaches a {@link Hunt} to a {@link Clue} by their respective ids.
   *
   * @param oauthHeader for user authentication.
   * @param huntId      is the Hunt to be added.
   * @param clueId      is the Clue to be modified.
   * @return the updated Clue on success.
   */
  @PUT("clues/{clueId}/hunt/{huntId}")
  Single<Clue> attachClueToHunt(@Header("Authorization") String oauthHeader,
      @Path("huntId") UUID huntId, @Path("clueId") UUID clueId);

  /**
   * This method deletes a single {@link Clue}.
   *
   * @param oauthHeader for user authentication.
   * @param uuid        identifies the Clue to be deleted.
   * @return a {@link Completable} to confirm the deletion.
   */
  @DELETE("clues/{id}")
  Completable deleteClue(@Header("Authorization") String oauthHeader, @Path("id") UUID uuid);

  // Multiple Clue Returns

  /**
   * This method gets all {@link Clue}s contained in a single {@link Hunt}.
   *
   * @param oauthHeader for user authentication.
   * @param id          identifies the Hunt to be searched.
   * @return the {@link List} of Clues in the Hunt.
   */
  @GET("/hunts/{id}/clues")
  List<Clue> getCluesByHunt(@Header("Authorization") String oauthHeader,
      @Path("id") UUID id);
  //endregion

//region HuntActivity CRUD methods
  // Single HuntActivity Returns

  /**
   * This method adds a new {@link HuntActivity} record to the server.
   *
   * @param oauthHeader  for user authentication.
   * @param huntActivity contains fields for a HuntActivity
   * @return the new HuntActivity on success.
   */
  @POST("hunt-activities/")
  Single<HuntActivity> postHuntActivity(@Header("Authorization") String oauthHeader,
      @Body HuntActivity huntActivity);

  /**
   * This method deletes a single {@link HuntActivity} record
   *
   * @param oauthHeader for user authentication.
   * @param id          identifies the record to be deleted
   * @return a {@link Completable} to verify the deletion.
   */
  @DELETE("hunt-activities/{id}")
  Completable deleteHuntActivity(@Header("Authorization") String oauthHeader,
      @Path("id") UUID id);

  /**
   * This method updates an existing {@link HuntActivity record}.  It accepts changes to the
   * completion time and number of clues completed.
   *
   * @param oauthHeader  for user authentication.
   * @param huntActivity contains fields to be updated.  It accepts empty or unchanged fields.
   * @return the updated HuntActivity.
   */
  @PUT("hunt-activities/{id}")
  Single<HuntActivity> putHuntActivity(@Header("Authorization") String oauthHeader,
      @Body HuntActivity huntActivity);

  // Multiple HuntActivity Returns

  /**
   * This method returns all {@link HuntActivity} records for a single {@link User}.  It can be used
   * to show Hunt history for an individual.
   *
   * @param oauthHeader for user authentication.
   * @param user        is the User's records to return.
   * @return a {@link List} of HuntActivities.
   */
  @GET("hunt-activities/search/{user}")
  List<HuntActivity> getHuntActivityByUser(@Header("Authorization") String oauthHeader,
      @Path("user") User user);

  /**
   * This method returns all {@link HuntActivity} records for a single {@link Hunt}. It can be used
   * to show a progress/leaderbard for a particular hunt.
   *
   * @param oauthHeader for user authentication.
   * @param hunt        is the Hunt to search by.
   * @return a {@link List} of HuntActivities.
   */
  @GET("hunt-activities/search/{hunt}")
  List<HuntActivity> getHuntActivityByHunt(@Header("Authorization") String oauthHeader,
      @Path("hunt") Hunt hunt);
//endregion

  //region Organizer CRUD methods

  /**
   * This method returns a specific {@link Organizer} by id.
   *
   * @param oauthHeader for user authentication.
   * @param id          is an Organizer's {@link UUID}.
   * @return an Organizer on success.
   */
  @GET("organizers/{id}")
  Single<Organizer> getOrganizer(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  /**
   * This method adds a new {@link Organizer} to the server.
   *
   * @param oauthHeader for user authentication.
   * @param organizer   is the new Organizer.
   * @return the new Organizer on success.
   */
  @POST("organizers/")
  Single<Organizer> postOrganizer(@Header("Authorization") String oauthHeader,
      @Body Organizer organizer);

  /**
   * This method deletes a single {@link Organizer} by id.
   *
   * @param oauthHeader for user authentication.
   * @param id          identifies the Organizer to be deleted.
   * @return a {@link Completable} to verify the deletion.
   */
  @DELETE("organizers/{id}")
  Completable deleteOrganizer(@Header("Authorization") String oauthHeader,
      @Path("id") UUID id);
//endregion

// region User CRUD methods

  /**
   * This method returns a specific {@link User} by id.
   *
   * @param oauthHeader for user authentication.
   * @param id          is a User's {@link UUID}.
   * @return a User on success.
   */
  @GET("users/{id}")
  Single<User> getUser(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  /**
   * This method adds a new {@link User} to the server.
   *
   * @param oauthHeader for user authentication.
   * @param user        is the new User.
   * @return the new User on success.
   */
  @POST("users/")
  Single<User> postUser(@Header("Authorization") String oauthHeader, @Body User user);

  /**
   * This method adds {@link Organizer} permission to an existing {@link User}.
   *
   * @param oauthHeader for user authentication.
   * @param userId      is the User to elevat
   * @param organizerId is the new Organizer profile
   * @return the updated User on success.
   */
  @PUT("users/{userId}/organizer/{organizerId}")
  Single<User> elevateUser(@Header("Authorization") String oauthHeader, @Path("userId") UUID userId,
      @Path("organizerId") UUID organizerId);
// endregion

  /**
   * This method is part of the singleton pattern that ensures there is only one ScavengrService
   * instance in operation.
   *
   * @return the singleton Instance.
   */
  static ScavengrService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * The InstanceHolder class is part of the singleton pattern that ensures there is only one
   * ScavengrService in operation.  It provides a static Instance with the necessary services.
   */
  class InstanceHolder {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final ScavengrService INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .setDateFormat(TIMESTAMP_FORMAT)
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .readTimeout(60, TimeUnit.SECONDS)
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(ScavengrService.class);
    }

  }

}
