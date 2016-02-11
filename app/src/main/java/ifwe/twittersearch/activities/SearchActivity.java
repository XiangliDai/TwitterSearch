package ifwe.twittersearch.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ifwe.twittersearch.R;
import ifwe.twittersearch.fragments.SearchFragment;

public class SearchActivity extends AppCompatActivity {
    private static String LOG = SearchActivity.class.getName();
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query= getIntent().getStringExtra("query");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.search) + " " + query);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) == null) {

            Fragment fragment = SearchFragment.newInstance(query);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
       // final MenuItem dialogMenuItem = menu.findItem(R.id.action_dialog);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(LOG, "onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(LOG, "onPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(LOG, "onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(LOG, "onDestroy");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_dialog:
                showDialog();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showDialog() {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // user hit cancel, so we stay in the current compose activity
                dialog.cancel();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.setTitle(getString(R.string.app_name));
        dialog.setMessage(getString(R.string.search));
        dialog.show();
    }
}
