package com.applovin.demoapp.interstitials;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.demoapp.BaseActivity;
import com.applovin.demoapp.R;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;

public class InterstitialManualLoadingActivity extends BaseActivity {

    private AppLovinAd currentAd;
    private AppLovinInterstitialAdDialog interstitialAdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_manual_loading);

        adStatusTextView = (TextView) findViewById(R.id.statusLabel);

        final AppLovinSdk sdk = AppLovinSdk.getInstance(getApplicationContext());
        interstitialAdDialog = AppLovinInterstitialAd.create(sdk, this);

        final Button loadButton = (Button) findViewById(R.id.loadButton);
        final Button showButton = (Button) findViewById(R.id.showButton);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                log("Interstitial loading...");
                showButton.setEnabled(false);

                AppLovinSdk.getInstance(getApplicationContext()).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd ad) {
                        log("Interstitial Loaded");

                        currentAd = ad;
                        showButton.setEnabled(true);
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        // Look at AppLovinErrorCodes.java for list of error codes
                        log("Interstitial failed to load with error code " + errorCode);
                    }
                });
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAd != null) {
                    interstitialAdDialog.showAndRender(currentAd);
                }
            }
        });

        //
        // Optional: Set ad display, ad click, and ad video playback callback listeners
        //
        interstitialAdDialog.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd) {
                log("Interstitial Displayed");
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd) {
                log("Interstitial Hidden");
            }
        });

        interstitialAdDialog.setAdClickListener(new AppLovinAdClickListener() {
            @Override
            public void adClicked(AppLovinAd appLovinAd) {
                log("Interstitial Clicked");
            }
        });

        // This will only ever be used if you have video ads enabled.
        interstitialAdDialog.setAdVideoPlaybackListener(new AppLovinAdVideoPlaybackListener() {
            @Override
            public void videoPlaybackBegan(AppLovinAd appLovinAd) {
                log("Video Started");
            }

            @Override
            public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed) {
                log("Video Ended");
            }
        });
    }
}
