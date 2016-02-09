package ifwe.twittersearch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;

import ifwe.twittersearch.MyTwitterApiClient;
import ifwe.twittersearch.MyTwitterSession;
import ifwe.twittersearch.R;
import ifwe.twittersearch.clients.SearchTimeLineService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends TwitterBaseFragment {

    private String query;
    public static SearchFragment newInstance(String query) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    protected void getTweetList() {
        TwitterSession session = MyTwitterSession.getInstance().getSession();
        MyTwitterApiClient twitterApiClient = new MyTwitterApiClient(session);
        SearchTimeLineService searchTimeLineService = twitterApiClient.getSearchTimeLineService();
        searchTimeLineService.show(query, new Callback<Search>() {

            @Override
            public void success(Result<Search> result) {
                tweetAdapter.addAll(result.data.tweets);
                tweetAdapter.notifyDataSetChanged();
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(SearchFragment.class.getName(), "get timeline failed:" + e.getMessage());
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.query = getArguments().getString("query");
        }
        this.fragmentId = R.layout.fragment_search;
    }


}
