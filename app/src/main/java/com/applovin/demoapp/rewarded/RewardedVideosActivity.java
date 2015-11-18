package com.applovin.demoapp.rewarded;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.demoapp.BaseActivity;
import com.applovin.demoapp.R;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;

import java.lang.ref.WeakReference;
import java.util.Map;

public class RewardedVideosActivity extends BaseActivity {

    private AppLovinIncentivizedInterstitial incentivizedInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_videos);

        adStatusTextView = (TextView) findViewById(R.id.statusLabel);

        final AppLovinSdk sdk = AppLovinSdk.getInstance(getApplicationContext());
        final Button loadButton = (Button) findViewById(R.id.loadButton);
        final Button showButton = (Button) findViewById(R.id.showButton);

        // Unlike interstitials, you need to preload each rewarded video before it can be displayed.

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                log("Rewarded video loading...");
                showButton.setEnabled(false);

                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(getApplicationContext());
                incentivizedInterstitial.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd) {
                        log("Rewarded video loaded.");
                        showButton.setEnabled(true);
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode) {
                        log("Rewarded video failed to load with error code " + errorCode);
                    }
                });
            }
        });

        final WeakReference<RewardedVideosActivity> weakRef = new WeakReference<RewardedVideosActivity>(this);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incentivizedInterstitial.isAdReadyToDisplay()) {

                    //
                    // OPTIONAL: Create listeners
                    //

                    // Reward Listener
                    AppLovinAdRewardListener adRewardListener = new AppLovinAdRewardListener() {
                        @Override
                        public void userRewardVerified(AppLovinAd appLovinAd, Map map) {
                            // AppLovin servers validated the reward. Refresh user balance from your server.  We will also pass the number of coins
                            // awarded and the name of the currency.  However, ideally, you should verify this with your server before granting it.

                            // i.e. - "Coins", "Gold", whatever you set in the dashboard.
                            String currencyName = (String) map.get("currency");

                            // For example, "5" or "5.00" if you've specified an amount in the UI.
                            String amountGivenString = (String) map.get("amount");

                            log("Rewarded " + amountGivenString + " " + currencyName);
                            log("");
                            // By default we'll show a alert informing your user of the currency & amount earned.
                            // If you don't want this, you can turn it off in the Manage Apps UI.
                        }

                        @Override
                        public void userOverQuota(AppLovinAd appLovinAd, Map map) {
                            // Your user has already earned the max amount you allowed for the day at this point, so
                            // don't give them any more money. By default we'll show them a alert explaining this,
                            // though you can change that from the AppLovin dashboard.
                        }

                        @Override
                        public void userRewardRejected(AppLovinAd appLovinAd, Map map) {
                            // Your user couldn't be granted a reward for this view. This could happen if you've blacklisted
                            // them, for example. Don't grant them any currency. By default we'll show them an alert explaining this,
                            // though you can change that from the AppLovin dashboard.
                        }

                        @Override
                        public void validationRequestFailed(AppLovinAd appLovinAd, int responseCode) {
                            if (responseCode == AppLovinErrorCodes.INCENTIVIZED_USER_CLOSED_VIDEO) {
                                // Your user exited the video prematurely. It's up to you if you'd still like to grant
                                // a reward in this case. Most developers choose not to. Note that this case can occur
                                // after a reward was initially granted (since reward validation happens as soon as a
                                // video is launched).
                            } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_SERVER_TIMEOUT || responseCode == AppLovinErrorCodes.INCENTIVIZED_UNKNOWN_SERVER_ERROR) {
                                // Some server issue happened here. Don't grant a reward. By default we'll show the user
                                // a alert telling them to try again later, but you can change this in the
                                // AppLovin dashboard.
                            } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_NO_AD_PRELOADED) {
                                // Indicates that the developer called for a rewarded video before one was available.
                                // Note: This code is only possible when working with rewarded videos.
                            }
                        }

                        @Override
                        public void userDeclinedToViewAd(AppLovinAd appLovinAd) {
                            // This method will be invoked if the user selected "no" when asked if they want to view an ad.
                            // If you've disabled the pre-video prompt in the "Manage Apps" UI on our website, then this method won't be called.
                        }
                    };

                    // Video Playback Listener
                    AppLovinAdVideoPlaybackListener adVideoPlaybackListener = new AppLovinAdVideoPlaybackListener() {
                        @Override
                        public void videoPlaybackBegan(AppLovinAd appLovinAd) {
                            log("Video Started");
                        }

                        @Override
                        public void videoPlaybackEnded(AppLovinAd appLovinAd, double v, boolean b) {
                            log("Video Ended");
                        }
                    };

                    // Ad Dispaly Listener
                    AppLovinAdDisplayListener adDisplayListener = new AppLovinAdDisplayListener() {
                        @Override
                        public void adDisplayed(AppLovinAd appLovinAd) {
                            log("Ad Displayed");
                        }

                        @Override
                        public void adHidden(AppLovinAd appLovinAd) {
                            log("Ad Dismissed");
                        }
                    };

                    // Ad Click Listener
                    AppLovinAdClickListener adClickListener = new AppLovinAdClickListener() {
                        @Override
                        public void adClicked(AppLovinAd appLovinAd) {
                            log("Ad Click");
                        }
                    };

                    incentivizedInterstitial.show(weakRef.get(), adRewardListener, adVideoPlaybackListener, adDisplayListener, adClickListener);
                }
            }
        });
    }
}
