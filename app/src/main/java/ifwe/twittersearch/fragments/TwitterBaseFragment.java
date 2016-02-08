package ifwe.twittersearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ifwe.twittersearch.R;
import ifwe.twittersearch.TweetAdapter;
import ifwe.twittersearch.Utils;


public abstract class TwitterBaseFragment extends Fragment {
    private static final int REQUEST_CODE = 10;

    protected ListView lvList;
    protected List<com.twitter.sdk.android.core.models.Tweet> tweetList;
    protected SwipeRefreshLayout swipeContainer;
    protected TweetAdapter tweetAdapter;
    protected int fragmentId;
    protected ProgressBar pb;

    public TwitterBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(fragmentId, container, false);

        lvList = (ListView) view.findViewById(R.id.lvList);

        tweetList = new ArrayList<>();
        tweetAdapter = new TweetAdapter(getActivity(), tweetList, null);
        lvList.setAdapter(tweetAdapter);

        View footerView = inflater.inflate(R.layout.footer_layout, null, false);
        lvList.addFooterView(footerView);
        pb = (ProgressBar) footerView.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.INVISIBLE);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              //  TODO get newer tweet;
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
         if(Utils.isNetworkAvailable(getActivity())) {
             getTweetList();

        }
        return view;
    }


    abstract protected void getTweetList();

}
