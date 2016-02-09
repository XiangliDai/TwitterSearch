package ifwe.twittersearch.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.Collection;

import ifwe.twittersearch.MyTwitterApiClient;
import ifwe.twittersearch.MyTwitterSession;
import ifwe.twittersearch.R;
import ifwe.twittersearch.clients.TimeLineService;

public class TimelineFragment extends TwitterBaseFragment {

    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
       
        return fragment;
    }

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        this.fragmentId = R.layout.fragment_timeline;
    }

    @Override
    protected void getTweetList() {

        TwitterSession session = MyTwitterSession.getInstance().getSession();
        MyTwitterApiClient twitterApiClient = new MyTwitterApiClient(session);
        TimeLineService timeLineService = twitterApiClient.getTimeLineService();
        timeLineService.getHomeTimeLine(new Callback<Collection<com.twitter.sdk.android.core.models.Tweet>>() {
            @Override
            public void success(Result<Collection<com.twitter.sdk.android.core.models.Tweet>> result) {
                ArrayList<com.twitter.sdk.android.core.models.Tweet> tweetArrayList = (ArrayList<com.twitter.sdk.android.core.models.Tweet>) result.data;
                Log.d(TimelineFragment.class.getName(), "tweetArrayList size:" + tweetArrayList.size());
                tweetAdapter.addAll(tweetArrayList);
                tweetAdapter.notifyDataSetChanged();
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(TimelineFragment.class.getName(), "get timeline failed:" + e.getMessage());
                pb.setVisibility(View.INVISIBLE);
            }
        });
      }

}
