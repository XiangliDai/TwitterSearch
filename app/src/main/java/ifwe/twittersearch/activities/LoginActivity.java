package ifwe.twittersearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import ifwe.twittersearch.MyTwitterSession;
import ifwe.twittersearch.R;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "rz20Rhb1TL1j1BZdGqgDcMdtr";
    private static final String TWITTER_SECRET = "cmSXQOGT7n32nq3hPqMXk4TO4C9aUdTvuPmrpcxcbJL6z0X1eD";

    TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        loginButton = (TwitterLoginButton)findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(LoginActivity.class.getName(), "we are logged in now!");

                TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                MyTwitterSession.getInstance().setSession(session);
                Intent intent = new Intent(LoginActivity.this, TimelineActivity.class);
                startActivity(intent);

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.e(LoginActivity.class.getName(), "login failed:" + exception.getLocalizedMessage());

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

    }
}
