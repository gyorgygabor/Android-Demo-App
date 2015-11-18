package com.applovin.demoapp.interstitials;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.demoapp.BaseActivity;
import com.applovin.demoapp.R;
import com.applovin.sdk.AppLovinSdk;

import java.lang.ref.WeakReference;

public class InterstitialSharedInstanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_shared_instance);

        adStatusTextView = (TextView)findViewById(R.id.statusLabel);

        final WeakReference<InterstitialSharedInstanceActivity> weakRef = new WeakReference<InterstitialSharedInstanceActivity>(this);

        final Button showButton = (Button) findViewById(R.id.loadButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppLovinInterstitialAd.isAdReadyToDisplay(weakRef.get())) {
                    // If you want to set the ad load, ad display, ad click, or video playback callback listeners, use one of the other methods to show
                    AppLovinInterstitialAd.show(weakRef.get());
                    log("Interstitial Displayed");
                } else {
                    // Ideally, the SDK preloads ads when you initialize it in your launch activity
                    // you can manually load an ad as demonstrated in InterstitialManualLoadingActivity
                    log("Interstitial not ready for display.\nPlease check SDK key or internet connection.");
                }
            }
        });
    }
}
