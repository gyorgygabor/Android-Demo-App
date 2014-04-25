package com.applovin.sdk.demo.advanced;

import android.widget.Toast;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

/**
 * Created with IntelliJ IDEA. User: mszaro Date: 4/24/14 Time: 2:09 PM
 */
public class ManualCachingFragment extends ManagedFragment
{
    private AppLovinSdk                  sdk;
    private AppLovinInterstitialAdDialog adDialog;

    private volatile AppLovinAd          lastAd;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_advanced_interstitials;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_caching;
    }

    @Override
    protected void assignViews()
    {

    }

    @Override
    protected void setUpFragment()
    {
        sdk = AppLovinSdk.getInstance( getActivity() );
        adDialog = AppLovinInterstitialAd.create( sdk, getActivity() );

        addMenuAction( R.id.preloadInterstitial, new MenuAction() {
            @Override
            public void onSelected()
            {
                loadAd();
            }
        } );

        addMenuAction( R.id.showPreloadedInterstitial, new MenuAction() {
            @Override
            public void onSelected()
            {
                if ( lastAd != null )
                {
                    adDialog.showAndRender( lastAd );
                    lastAd = null;
                }
                else
                {
                    Toast.makeText( getActivity(), "No ad preloaded!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    @Override
    protected void recreateFragment()
    {
    }

    private void loadAd()
    {
        sdk.getAdService().loadNextAd( AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd)
            {
                lastAd = appLovinAd;
                Toast.makeText( getActivity(), "Interstitial manually loaded.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void failedToReceiveAd(int i)
            {
                Toast.makeText( getActivity(), "Interstitial load failed: " + i, Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
