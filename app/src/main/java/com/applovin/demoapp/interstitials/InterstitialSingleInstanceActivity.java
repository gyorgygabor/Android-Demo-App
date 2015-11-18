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
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;

import java.lang.ref.WeakReference;

public class InterstitialSingleInstanceActivity extends BaseActivity {
    private AppLovinInterstitialAdDialog interstitialAdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_single_instance);

        adStatusTextView = (TextView) findViewById(R.id.statusLabel);

        final WeakReference<InterstitialSingleInstanceActivity> weakRef = new WeakReference<InterstitialSingleInstanceActivity>(this);
        final AppLovinSdk sdk = AppLovinSdk.getInstance(getApplicationContext());

        final Button showButton = (Button) findViewById(R.id.loadButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppLovinInterstitialAd.isAdReadyToDisplay(weakRef.get())) {
                    interstitialAdDialog = AppLovinInterstitialAd.create(sdk, weakRef.get());

                    //
                    // Optional: Set ad load, ad display, ad click, and ad video playback callback listeners
                    //
                    interstitialAdDialog.setAdLoadListener(new AppLovinAdLoadListener() {
                        @Override
                        public void adReceived(AppLovinAd appLovinAd) {
                            log("Interstitial loaded");
                        }

                        @Override
                        public void failedToReceiveAd(int errorCode) {

                            // Look at AppLovinErrorCodes.java for list of error codes

                            if (errorCode == AppLovinErrorCodes.NO_FILL) {
                                log("No-fill: No ads are currently available for this device/country");
                            } else {
                                log("Interstitial failed to load with error code " + errorCode);
                            }
                        }
                    });

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

                    interstitialAdDialog.show();
                } else {
                    // Ideally, the SDK preloads ads when you initialize it in your launch activity
                    // you can manually load an ad as demonstrated in InterstitialManualLoadingActivity
                    log("Interstitial not ready for display.\nPlease check SDK key or internet connection.");
                }
            }
        });
    }
}
