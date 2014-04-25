package com.applovin.sdk.demo.advanced;

import android.app.Activity;
import android.widget.Toast;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

/**
 * Created by mszaro on 4/16/14.
 */
public class ProgrammaticInterstitialFragment extends ManagedFragment
{
    private AppLovinInterstitialAdDialog interstitialAdDialog;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_advanced_interstitials;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_interstitials;
    }

    @Override
    protected void assignViews()
    {
    }

    @Override
    protected void setUpFragment()
    {
        final Activity activity = getActivity();
        final AppLovinSdk sdk = AppLovinSdk.getInstance( activity );

        interstitialAdDialog = AppLovinInterstitialAd.create( sdk, activity );

        interstitialAdDialog.setAdLoadListener( new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Interstitial loaded.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void failedToReceiveAd(int errorCode)
            {
                if ( errorCode == AppLovinErrorCodes.NO_FILL )
                {
                    Toast.makeText( getActivity(), "No-fill: No ads are currently available for this device/country. " + errorCode, Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    Toast.makeText( getActivity(), "Banner failed to load: " + errorCode, Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        interstitialAdDialog.setAdDisplayListener( new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Interstitial displayed.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Interstitial hidden.", Toast.LENGTH_SHORT ).show();
            }
        } );

        interstitialAdDialog.setAdClickListener( new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Interstitial clicked.", Toast.LENGTH_SHORT ).show();
            }
        } );

        interstitialAdDialog.setAdVideoPlaybackListener( new AppLovinAdVideoPlaybackListener() {
            @Override
            public void videoPlaybackBegan(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Interstitial video playback started.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
            {
                Toast.makeText( getActivity(), "Interstitial video playback ended.", Toast.LENGTH_SHORT ).show();
            }
        } );

        addMenuAction( R.id.loadInterstitial, new MenuAction() {
            @Override
            public void onSelected()
            {
                interstitialAdDialog.show();
            }
        } );
    }

    @Override
    protected void recreateFragment()
    {
    }
}
