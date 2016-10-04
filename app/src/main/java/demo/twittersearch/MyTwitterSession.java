package demo.twittersearch;

import com.twitter.sdk.android.core.TwitterSession;

public class MyTwitterSession {
    private static MyTwitterSession instance = null;


    private TwitterSession session;
    private long userId;
    protected MyTwitterSession() {
        // Exists only to defeat instantiation.
    }

    public static MyTwitterSession getInstance() {
        if (instance == null) {
            instance = new MyTwitterSession();
        }
        return instance;
    }

    public void setSession(TwitterSession session) {
        this.session = session;
    }

    public TwitterSession getSession() {
        return this.session;

    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;

    }

}
