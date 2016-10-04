package demo.twittersearch.clients;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Search;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SearchTimeLineService{
    @GET("/1.1/search/tweets.json")
    void show(@Query("q") String query, Callback<Search> cb);

    @GET("/1.1/search/tweets.json")
    void showOlder(@Query("q") String query, @Query("max_id") long maxId, Callback<Search> cb);
}
