package demo.twittersearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;

import demo.twittersearch.EndlessScrollListener;
import demo.twittersearch.MyTwitterApiClient;
import demo.twittersearch.R;
import demo.twittersearch.adapters.TweetAdapter;
import demo.twittersearch.Utils;
import demo.twittersearch.clients.SearchTimeLineService;
import demo.twittersearch.clients.TimeLineService;


public abstract class TwitterBaseFragment extends Fragment {
    private static final int REQUEST_CODE = 10;

    protected ListView lvList;
    protected SwipeRefreshLayout swipeContainer;
    protected TweetAdapter tweetAdapter;

    protected long maxId;

    abstract int getFragmentId();

    protected ProgressBar pb;
    protected TimeLineService timeLineService;
    protected SearchTimeLineService searchTimeLineService;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.white);
         timeLineService =  MyTwitterApiClient.getInstance().getTimeLineService();
        searchTimeLineService = MyTwitterApiClient.getInstance().getSearchTimeLineService();
        tweetAdapter = new TweetAdapter(getActivity(), new ArrayList<Tweet>(), null);
        lvList.setAdapter(tweetAdapter);
        lvList.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getOlderTweetList();
            }
        });

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(getFragmentId(), container, false);
        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        lvList = (ListView) view.findViewById(R.id.lvList);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);


        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    protected abstract void getOlderTweetList();


    abstract protected void getTweetList();

}
