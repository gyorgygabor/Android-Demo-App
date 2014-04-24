package com.applovin.sdk.demo.simple;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

/**
 * Created by mszaro on 4/16/14.
 */
public class InterstitialFragment extends ManagedFragment
{
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_interstitials;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_interstitials;
    }

    @Override
    protected void assignViews() {}

    @Override
    protected void setUpFragment()
    {
        addMenuAction( R.id.loadInterstitial, new MenuAction() {
            @Override
            public void onSelected()
            {
                AppLovinInterstitialAd.show( getActivity() );
            }
        } );
    }

    @Override
    protected void recreateFragment()
    {
    }
}
