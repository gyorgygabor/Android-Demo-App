package com.applovin.sdk.demo.simple;

import com.applovin.adview.AppLovinAdView;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

/**
 * Created by mszaro on 4/16/14.
 */
public class BannerFragment extends ManagedFragment
{
    private AppLovinAdView mAdView;

    public void loadNewBanner()
    {
        mAdView.loadNextAd();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_banners;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_banners;
    }

    @Override
    protected void assignViews()
    {
        mAdView = (AppLovinAdView) findFragmentViewById( R.id.adView );
    }

    @Override
    protected void setUpFragment()
    {
        addMenuAction( R.id.loadBanner, new MenuAction() {
            @Override
            public void onSelected()
            {
                loadNewBanner();
            }
        } );
    }

    @Override
    protected void recreateFragment()
    {
        mAdView.loadNextAd();
    }
}
