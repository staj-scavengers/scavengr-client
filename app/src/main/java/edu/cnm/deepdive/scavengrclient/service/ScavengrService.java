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

//  Hunt CRUD methods

  //Single Hunt Returns

  @GET("hunts/{id}")
  Single<Hunt> getHunt(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @POST("hunts/")
  Single<Hunt> postHunt(@Header("Authorization") String oauthHeader, @Body Hunt hunt);

  @PUT("hunts/{id}")
  Single<Hunt> putHunt(@Header("Authorization") String oauthHeader, @Body Hunt hunt,
      @Path("id") UUID id);

  @DELETE("hunts/{id}")
  Completable deleteHunt(@Header("Authorization") String oauthHeader, @Path("id") UUID uuid);

  //Multipe Hunt Returns

  @GET("hunts/")
  Iterable<Hunt> getHuntsByOrganizer(@Header("Authorization") String oauthHeader,
      @Query("organizer") Organizer organizer);

  @GET("hunts/search")
  Iterable<Hunt> searchHuntsByName(@Header("Authorization") String oauthHeader,
      @Query("name") String huntName);

// Clue CRUD methods

  //Single Clue Returns

  @GET("clues/{id}")
  Single<Clue> getClue(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @POST("clues/")
  Single<Clue> postClue(@Header("Authorization") String oauthHeader, @Body Clue clue);

  @PUT("clues/{id}")
  Single<Clue> putClue(@Header("Authorization") String oauthHeader, @Path("id") UUID id,
      @Body Clue clue);

  @PUT("clues/{clueId}/hunt/{huntId}")
  Single<Clue> attachClueToHunt(@Header("Authorization") String oauthHeader,
      @Path("huntId") UUID huntId, @Path("clueId") UUID clueId);

  @DELETE("clues/{id}")
  Completable deleteClue(@Header("Authorization") String oauthHeader, @Path("id") UUID uuid);

  // Multiple Clue Returns

  @GET("clues/search")
  Iterable<Clue> getCluesByHunt(@Header("Authorization") String oauthHeader,
      @Query("hunt") Hunt hunt);

// HuntActivity CRUD methods

  // Single HuntActivity Returns

  @POST("hunt-activities/")
  Single<HuntActivity> postHuntActivity(@Header("Authorization") String oauthHeader,
      @Body HuntActivity huntActivity);

  @DELETE("hunt-activities/{id}")
  Completable deleteHuntActivity(@Header("Authorization") String oauthHeader,
      @Path("id") UUID uuid);

  @PUT("hunt-activities/{id}")
  Single<HuntActivity> putHuntActivity(@Header("Authorization") String oauthHeader, @Body HuntActivity huntActivity);

  // Multiple HuntActivity Returns

  //FIXME with HuntActivityController getByUser
  @GET("hunt-activities/search/{user}")
  Iterable<HuntActivity> getHuntActivityByUser(@Header("Authorization") String oauthHeader,
      @Path("user") User user);


  //FIXME with HuntActivityController getByHunt
  @GET("hunt-activities/search/{hunt}")
  Iterable<HuntActivity> getHuntActivityByHunt(@Header("Authorization") String oauthHeader,
      @Path("hunt") Hunt hunt);

// Organizer CRUD methods

  @GET("organizers/{id}")
  Single<Organizer> getOrganizer(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @POST("organizers/")
  Single<Organizer> postOrganizer(@Header("Authorization") String oauthHeader,
      @Body Organizer organizer);

  @DELETE("organizers/{id}")
  Single<Organizer> deleteOrganizer(@Header("Authorization") String oauthHeader,
      @Path("id") UUID id);

// User CRUD methods

  @POST("users/")
  Single<User> postUser(@Header("Authorization") String oauthHeader, @Body Organizer organizer);

  @PUT("users/{userId}/organizer/{organizerId}")
  Single<User> elevateUser(@Header("Authorization") String oauthHeader, @Path("userId") UUID userId,
      @Path("organizerId") UUID organizerId);

  class InstanceHolder {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final ScavengrService INSTANCE;

    static {
      Gson gson  = new GsonBuilder()
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
