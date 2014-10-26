package net.lion_stuido.lionstudio.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.fragments.AlbumGridFragment;
import net.lion_stuido.lionstudio.fragments.NavigationDrawerFragment;
import net.lion_stuido.lionstudio.fragments.NewsFragment;
import net.lion_stuido.lionstudio.model.Setting;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;
import static net.lion_stuido.lionstudio.utils.Constants.SETTING_URL;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_DOMAIN = "pict_domain";
    private static final String TAG = "MainActivity";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        String[] titles = getResources().getStringArray(R.array.nav_items_titles);
        switch (position) {
            case 0:
                mTitle = titles[0];
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NewsFragment.newInstance())
                        .commit();
                break;
            case 1:
                mTitle = titles[1];
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AlbumGridFragment.newInstance())
                        .commit();
                break;
            case 2:
                mTitle = titles[2];
                break;
            case 3:
                mTitle = titles[3];
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setPictDomainIfNeeded();
    }

    private void setPictDomainIfNeeded() {
        final SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        GsonRequest<Setting> urlReq = new GsonRequest<Setting>(DEFAULT_DOMAIN + SETTING_URL, Setting.class, new Response.Listener<Setting>() {
            @Override
            public void onResponse(Setting setting) {
                String domain = setting.getUrl();
                Editor editor = mSettings.edit();
                if (!mSettings.getString(APP_PREFERENCES_DOMAIN, "").equals(domain)) {
                    editor.putString(APP_PREFERENCES_DOMAIN, domain);
                    editor.commit();
                }

                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(urlReq);
    }
}
