package com.applovin.sdk.demo.advanced;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.applovin.adview.AppLovinAdView;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

/**
 * Created by mszaro on 4/16/14.
 */
public class ProgrammaticBannerFragment extends ManagedFragment
{
    private LinearLayout   mBannerContainer;
    private AppLovinAdView mAdView;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_advanced_banners;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_banners;
    }

    @Override
    protected void assignViews()
    {
        mBannerContainer = (LinearLayout) findFragmentViewById( R.id.banner_container );
    }


    @Override
    protected void setUpFragment()
    {
        mAdView = new AppLovinAdView( AppLovinAdSize.BANNER, getActivity() );

        mAdView.setAdLoadListener( new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Banner loaded.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void failedToReceiveAd(int i)
            {
                Toast.makeText( getActivity(), "Banner failed to load: " + i, Toast.LENGTH_SHORT ).show();
            }
        } );

        mAdView.setAdDisplayListener( new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Banner displayed.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Banner hidden.", Toast.LENGTH_SHORT ).show();
            }
        } );

        mAdView.setAdClickListener( new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Banner clicked.", Toast.LENGTH_SHORT ).show();
            }
        } );

        mBannerContainer.addView( mAdView );

        addMenuAction( R.id.loadBanner, new MenuAction() {
            @Override
            public void onSelected()
            {
                mAdView.loadNextAd();
            }
        } );
    }

    @Override
    protected void recreateFragment()
    {
        mAdView.loadNextAd();
    }
}
