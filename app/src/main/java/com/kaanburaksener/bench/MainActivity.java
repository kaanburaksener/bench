package com.kaanburaksener.bench;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.view.ViewPager;

import com.kaanburaksener.bench.service.ApplicationResultService;
import com.kaanburaksener.bench.ui.adapter.ViewPagerAdapter;
import com.kaanburaksener.bench.ui.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private int numberOftabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        startService();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        // Creating The Toolbar and setting it as the Toolbar for the activity
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(0);
        setActionBarTitle(getString(R.string.title_browse_requests)); //First fragment title

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), numberOftabs, this);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String currentTitle = "";

                if(position == 0) {
                    currentTitle = getString(R.string.title_browse_requests);
                } else if(position == 1) {
                    currentTitle = getString(R.string.title_make_request);
                } else {
                    currentTitle = getString(R.string.title_my_account);
                }

                setActionBarTitle(currentTitle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabs_scroll_color);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to start the service
    public void startService() {
        startService(new Intent(getBaseContext(), ApplicationResultService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
