package com.applovin.demoapp.nativeads;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.applovin.demoapp.BaseActivity;
import com.applovin.demoapp.R;
import com.applovin.demoapp.nativeads.carouselui.AppLovinCarouselView;
import com.pkmmte.pkrss.Article;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This activity simply inflates a default XML file that contains a carousel view, which will load its own ads into it.
 */
public class MultipleNativeAdsActivity extends BaseActivity {

    private RecyclerView                             recyclerView;
    private List<Article> feedItems = new ArrayList<Article>();
    private Set<WeakReference<AppLovinCarouselView>> onScreenCarousels = new HashSet<>();
    private MultipleNativeAdsActivity                selfRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_native_ads);

    }

    @Override
    protected void onStop()
    {
        super.onStop();

        for (final WeakReference<AppLovinCarouselView> carouselViewRef : onScreenCarousels)
        {
            final AppLovinCarouselView carouselView = carouselViewRef.get();
            if ( carouselView != null )
            {
                carouselView.onStop( this );
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        for (final WeakReference<AppLovinCarouselView> carouselViewRef : onScreenCarousels)
        {
            final AppLovinCarouselView carouselView = carouselViewRef.get();
            if ( carouselView != null )
            {
                carouselView.onResume( this );
            }
        }
    }
}
