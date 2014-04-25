package com.applovin.sdk.demo.appComponents;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.advanced.ManualCachingFragment;
import com.applovin.sdk.demo.advanced.ProgrammaticBannerFragment;
import com.applovin.sdk.demo.advanced.ProgrammaticInterstitialFragment;
import com.applovin.sdk.demo.advanced.RewardedVideoFragment;
import com.applovin.sdk.demo.simple.BannerFragment;
import com.applovin.sdk.demo.simple.InterstitialFragment;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener
{
    /**
     * The serialization (saved instance state) Bundle key representing the current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled( false );
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                "XML Banners",
                                "Simple Interstitials",
                                "Programmatic Banners",
                                "Advanced Interstitials",
                                "Manual Precaching",
                                "Rewarded Videos"
                        } ),
                this );
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        // Restore the previously serialized current dropdown position.
        if ( savedInstanceState.containsKey( STATE_SELECTED_NAVIGATION_ITEM ) )
        {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt( STATE_SELECTED_NAVIGATION_ITEM ) );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        // Serialize the current dropdown position.
        outState.putInt( STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex() );
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id)
    {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        getFragmentManager().beginTransaction()
                .replace( R.id.container, createFragment( position ) )
                .commit();
        return true;
    }

    public Fragment createFragment(int position)
    {
        switch (position)
        {
            case 0:
                return new BannerFragment();

            case 1:
                return new InterstitialFragment();

            case 2:
                return new ProgrammaticBannerFragment();

            case 3:
                return new ProgrammaticInterstitialFragment();

            case 4:
                return new ManualCachingFragment();

            case 5:
                return new RewardedVideoFragment();
        }

        return null;
    }
}
