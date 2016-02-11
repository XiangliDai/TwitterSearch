package ifwe.twittersearch.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;

import ifwe.twittersearch.R;

public class TimelineFragment extends TwitterBaseFragment {
    private static int COUNT = 20;

    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
       
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    int getFragmentId() {
        return R.layout.fragment_timeline;
    }


    @Override
    protected void getOlderTweetList() {
        Log.d(TimelineFragment.class.getName(), "maxId: " + maxId);
        timeLineService.getOlderHomeTimeLine(COUNT, maxId, getTweetCallback());
    }


    private Callback<Collection<Tweet>> tweetCallback;
    protected Callback<Collection<Tweet>> getTweetCallback() {
        if (tweetCallback == null) {
            tweetCallback = new Callback<Collection<com.twitter.sdk.android.core.models.Tweet>>() {
                @Override
                public void success(Result<Collection<Tweet>> result) {
                    ArrayList<Tweet> tweetArrayList = (ArrayList<com.twitter.sdk.android.core.models.Tweet>) result.data;
                    Log.d(TimelineFragment.class.getName(), "tweetArrayList size:" + tweetArrayList.size());
                    maxId = tweetArrayList.get(tweetArrayList.size() - 1).getId();
                    tweetAdapter.addAll(tweetArrayList);
                   pb.setVisibility(View.INVISIBLE);
                }

                @Override
                public void failure(TwitterException e) {
                    Log.d(TimelineFragment.class.getName(), "get timeline failed:" + e.getMessage());
                    pb.setVisibility(View.INVISIBLE);
                }
            };
        }
        return tweetCallback;
    }
    @Override
    protected void getTweetList() {
        timeLineService.getHomeTimeLine(COUNT, getTweetCallback());
      }

}
