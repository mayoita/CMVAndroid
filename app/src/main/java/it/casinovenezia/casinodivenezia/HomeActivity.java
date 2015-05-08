package it.casinovenezia.casinodivenezia;


import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import it.casinovenezia.adapter.NavDrawerListAdapter;
import it.casinovenezia.it.casinovenezia.model.NavDrawerItem;

/**
 * Created by massimomoro on 24/03/15.
 */
public class HomeActivity extends ActionBarActivity implements EventDetails.OnEventsInteractionListener,
        CasinoGame.OnGameInteractionListener,
        Tournament.OnTournamentInteractionListener,
        Restaurant.OnRestaurantInteractionListener,
        Map.OnMapInteractionListener,
        Newsletter.OnNewsletterInteractionListener,
        Subscription.OnSubscriptionInteractionListener,
        Facebook.OnFacebookInteractionListener,
        Timetable.OnTimetableInteractionListener,
        LogIn.OnLogInInteractionListener,
        EventsFr.ListEventItemClickListener



{
    protected OnBackPressedListener onBackPressedListener;

    //slide menu titles
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //nav drawer title
    private CharSequence mDrawerTitle;
    //use to store App title
    private CharSequence mTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        //Drawer inizialization
        mTitle = mDrawerTitle = getTitle();
        //load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        //nav drawer icons from resource
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);


        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                //R.drawable.ic_drawer,  http://stackoverflow.com/questions/27581764/actionbardrawertoggle-no-suitable-constructor-drawable
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            displayView(0);
        }

    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeMainFr();
                break;
            case 1:
                fragment = new LogIn();
                break;
            case 2:
                fragment = new Newsletter();
                break;
            case 3:
                fragment = new Facebook();
                break;
            case 4:
                fragment = new Mobile();
                break;
            case 5:
                fragment = new Vpc();
                break;
            case 6:
                fragment = new Subscription();
                break;
            case 7:
                fragment = new Timetable();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.e("MainActivity", "Error in creating fragment");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e("MainActivity", "Error in creating fragment");
    }

    @Override
    public void onListFragmentItemClick(int position) {
        Log.e("MainActivity", "Error in creating fragment");
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_changevenue:
                changeVenue();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void changeVenue() {

    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
           switch (onBackPressedListener.getClass().getSimpleName()) {
               case "CasinoGame":
                   onBackPressedListener.doBack();
                   onBackPressedListener = null;
                   break;
           }

       // else
           // super.onBackPressed();

    }


}