package ifwe.twittersearch;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import ifwe.twittersearch.clients.SearchTimeLineService;
import ifwe.twittersearch.clients.TimeLineService;

public class MyTwitterApiClient extends TwitterApiClient {
    public static MyTwitterApiClient client;
    private MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public static MyTwitterApiClient getInstance(){
        if(client == null){
            TwitterSession session = MyTwitterSession.getInstance().getSession();

            client = new MyTwitterApiClient(session);
        }
        return client;
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

