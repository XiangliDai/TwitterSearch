package ifwe.twittersearch;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import ifwe.twittersearch.clients.SearchTimeLineService;
import ifwe.twittersearch.clients.TimeLineService;

public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }
    /**
     * Provide user timeline with defined endpoints
     */
    public TimeLineService getTimeLineService() {
        return getService(TimeLineService.class);
    }

    /**
     * Provide user timeline with defined endpoints
     */
    public SearchTimeLineService getSearchTimeLineService() {
        return getService(SearchTimeLineService.class);
    }
}

