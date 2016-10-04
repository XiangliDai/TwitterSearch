package demo.twittersearch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;

import demo.twittersearch.R;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeTimelineFragment extends TwitterTimeLineBaseFragment {
    private static int COUNT = 20;

    public static HomeTimelineFragment newInstance() {
        HomeTimelineFragment fragment = new HomeTimelineFragment();
       
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    int getFragmentId() {
        return R.layout.fragment_timeline_recyclerview;
    }


    @Override
    protected void getOlderTweetList() {
        Log.d(HomeTimelineFragment.class.getName(), "maxId: " + maxId);

        timeLineService.getOlderHomeTimeLine(COUNT, maxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action());
    }


    @Override
    protected void getTweetList() {
        timeLineService.getHomeTimeLine(COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action());
    }

}
