package demo.twittersearch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;

import demo.twittersearch.R;

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


    @Override
    int getFragmentId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void getOlderTweetList() {
        searchTimeLineService.showOlder(query, maxId, getSearchCallback());
    }

    @Override
    protected void getTweetList() {
        searchTimeLineService.show(query, getSearchCallback());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.query = getArguments().getString("query");
        }
    }
    private Callback<Search> searchCallback;
    protected Callback<Search> getSearchCallback() {
        if(searchCallback == null){
            searchCallback = new Callback<Search>() {
                @Override
                public void success(Result<Search> result) {
                    tweetAdapter.addAll(result.data.tweets);
                    pb.setVisibility(View.INVISIBLE);
                    maxId = result.data.tweets.get(result.data.tweets.size() - 1).getId();
                }

                @Override
                public void failure(TwitterException e) {
                    Log.d(SearchFragment.class.getName(), "get search result failed:" + e.getMessage());
                    pb.setVisibility(View.INVISIBLE);
                }
            };
        }
        return  searchCallback;
    }
}
