package demo.twittersearch.clients;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.Collection;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface TimeLineService{
    @GET("/1.1/statuses/user_timeline.json")
    void getUserTimeLine(@Query("user_id") long id, Callback<Collection<Tweet>> cb);

    @GET("/1.1/statuses/home_timeline.json")
    void getHomeTimeLine(@Query("count") int count, Callback<Collection<Tweet>> cb);
    @GET("/1.1/statuses/home_timeline.json")
    void getOlderHomeTimeLine(@Query("count") int count, @Query("max_id") long maxId, Callback<Collection<Tweet>> cb);

    @GET("/1.1/statuses/user_timeline.json")
    Observable<Collection<Tweet>>  getUserTimeLine(@Query("user_id") long id, @Query("count") int count);


    @GET("/1.1/statuses/user_timeline.json")
    Observable<Collection<Tweet>>  getOlderUserTimeLine(@Query("user_id") long id, @Query("count") int count, @Query("max_id") long maxId);

    @GET("/1.1/statuses/home_timeline.json")
    Observable<Collection<Tweet>>  getHomeTimeLine(@Query("count") int count);

    @GET("/1.1/statuses/home_timeline.json")
    Observable<Collection<Tweet>>  getOlderHomeTimeLine(@Query("count") int count, @Query("max_id") long maxId);
}
