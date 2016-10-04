package demo.twittersearch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;

import demo.twittersearch.EndlessRecyclerViewScrollListener;
import demo.twittersearch.EndlessScrollListener;
import demo.twittersearch.MyTwitterApiClient;
import demo.twittersearch.R;
import demo.twittersearch.Utils;
import demo.twittersearch.adapters.TweetAdapter;
import demo.twittersearch.adapters.TweetRecyclerViewAdapter;
import demo.twittersearch.clients.SearchTimeLineService;
import demo.twittersearch.clients.TimeLineService;
import rx.Observable;
import rx.functions.Action1;


public abstract class TwitterTimeLineBaseFragment extends Fragment {
    private static final int REQUEST_CODE = 10;

    protected RecyclerView lvList;
    protected SwipeRefreshLayout swipeContainer;
    protected TweetRecyclerViewAdapter tweetAdapter;

    protected long maxId;

    abstract int getFragmentId();

    protected ProgressBar pb;
    protected TimeLineService timeLineService;
    protected SearchTimeLineService searchTimeLineService;
    protected ArrayList<Tweet> tweets;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setBackgroundDrawable(null);
        timeLineService =  MyTwitterApiClient.getInstance().getTimeLineService();
        searchTimeLineService = MyTwitterApiClient.getInstance().getSearchTimeLineService();
        tweets = new ArrayList();
        tweetAdapter = new TweetRecyclerViewAdapter(getActivity(), tweets);
        tweetAdapter.setItemClickListener(new TweetRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClicked(int position) {

            }
        });
        lvList.setAdapter(tweetAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTweetList();
                //  TODO get newer tweet;
            }
        });

        if(Utils.isNetworkAvailable(getActivity())) {
            getTweetList();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentId(), container, false);
        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        lvList = (RecyclerView) view.findViewById(R.id.rvList);
        lvList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lvList.setLayoutManager(linearLayoutManager);
        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getOlderTweetList();
            }
        });
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @NonNull
    protected Action1<Collection<Tweet>> action() {
        return new Action1<Collection<Tweet>>() {
            @Override
            public void call(Collection<Tweet> items) {
                ArrayList<Tweet> tweetArrayList = (ArrayList<Tweet>) items;
                Log.d(UserTimelineFragment.class.getName(), "tweets size:" + tweetArrayList.size());
                maxId = tweetArrayList.get(tweetArrayList.size() - 1).getId();
                tweets.addAll(tweetArrayList);
                tweetAdapter.notifyDataSetChanged();
                pb.setVisibility(View.INVISIBLE);
            }
        };
    }

    protected abstract void getOlderTweetList();


    protected abstract void getTweetList();

}
