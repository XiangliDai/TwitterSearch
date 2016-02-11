package ifwe.twittersearch.clients;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.Collection;

import retrofit.http.GET;
import retrofit.http.Query;

public interface TimeLineService{
    @GET("/1.1/statuses/user_timeline.json")
    void getUserTimeLine(@Query("user_id") long id, Callback<Collection<Tweet>> cb);

    @GET("/1.1/statuses/home_timeline.json")
    void getHomeTimeLine(@Query("count") int count, Callback<Collection<Tweet>> cb);
    @GET("/1.1/statuses/home_timeline.json")
    void getOlderHomeTimeLine(@Query("count") int count, @Query("max_id") long maxId, Callback<Collection<Tweet>> cb);

}
