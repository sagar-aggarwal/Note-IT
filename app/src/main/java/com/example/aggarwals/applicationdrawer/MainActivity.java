package com.example.aggarwals.applicationdrawer;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ListView mListView;
    private android.support.v7.widget.Toolbar tbar = null;
    private LinearLayout linearLayout;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles = {"Notes","Reminders","Archive","About"};
    private Integer[] navMenuIcons = {R.drawable.ic_note_add_black_24dp,R.drawable.ic_alarm_black_24dp,R.drawable.ic_archive_black_24dp,R.drawable.ic_help_outline_black_24dp};

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    Fragment f = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ForceMenuKeyField
        ForceMenuKeyField();

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mListView = (ListView)findViewById(R.id.drawer_left);
        tbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        linearLayout = (LinearLayout)findViewById(R.id.linear_draw);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons[0]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons[1]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons[2]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons[3]));

        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mListView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff9331")));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                tbar,
                R.string.drawer_open,
                R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        if (savedInstanceState == null){
            Notes notes = new Notes();
            getFragmentManager().beginTransaction().add(R.id.frame_main, notes).commit();
            setTitle("Notes");
        }
        mListView.setOnItemClickListener(new DrawerClickListener());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return  false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public class DrawerClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0: f = new Notes();
                    break;
                case 1:f = new RemainderFragment();
                        break;
                case 2: f = new ArchiveFragment();
                    break;
                case 3 :f = new AboutUsFragment();
                    break;
            }
            if (f != null){
                getFragmentManager().beginTransaction().replace(R.id.frame_main,f).commit();
                Log.d("MainActity", "Change Of Frame");
                mListView.setItemChecked(i, true);
                setTitle(navMenuTitles[i]);
                mDrawerLayout.closeDrawer(linearLayout);
            }
            else {
                Log.d("MainActivity","Error Creating Fragment");
            }
        }
    }

    private void ForceMenuKeyField(){
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }
    }
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
