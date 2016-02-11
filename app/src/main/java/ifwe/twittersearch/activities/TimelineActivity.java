package ifwe.twittersearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ifwe.twittersearch.R;
import ifwe.twittersearch.fragments.TimelineFragment;

public class TimelineActivity extends AppCompatActivity {
    private static final String TAG = TimelineActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Timeline");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) == null) {

            Fragment fragment = TimelineFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder, fragment)
                    .commit();


        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && query.length() > 0) {
                    // added to minimize background color flicker navigating to inbox search fragment
                    launchSearchActivity(query);
                    //clear query
                    searchView.setQuery("", false);
                    searchMenuItem.collapseActionView();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void launchSearchActivity(String query) {
        Intent intent = new Intent(this, SearchActivity.class);

        intent.putExtra("query", query);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
