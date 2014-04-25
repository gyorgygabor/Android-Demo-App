package com.applovin.sdk.demo.advanced;

import android.widget.TextView;
import android.widget.Toast;
import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.demo.R;
import com.applovin.sdk.demo.appComponents.ManagedFragment;
import com.applovin.sdk.demo.appComponents.MenuAction;

import java.util.Map;

/**
 * Created by mszaro on 4/16/14.
 */
public class RewardedVideoFragment extends ManagedFragment implements AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener, AppLovinAdRewardListener
{
    private TextView                         mCoinsText;

    private AppLovinIncentivizedInterstitial mInterstitial;
    private volatile boolean                 mVideoReady;
    private float                            mCoinsEarned;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_rewarded_vid;
    }

    @Override
    protected int getMenuId()
    {
        return R.menu.menu_rewarded_videos;
    }

    @Override
    protected void assignViews()
    {
        mCoinsText = (TextView) findFragmentViewById( R.id.textCoins );
    }

    @Override
    protected void setUpFragment()
    {
        addMenuAction( R.id.loadRewarded, new MenuAction() {
            @Override
            public void onSelected()
            {
                loadRewardedVideo();
            }
        } );

        addMenuAction( R.id.showRewarded, new MenuAction() {
            @Override
            public void onSelected()
            {
                showRewardedVideo();
            }
        } );

        mInterstitial = new AppLovinIncentivizedInterstitial( getActivity() );
    }

    private void loadRewardedVideo()
    {
        mInterstitial.preload( new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd)
            {
                Toast.makeText( getActivity(), "Rewarded video loaded", Toast.LENGTH_SHORT ).show();
                mVideoReady = true;
            }

            @Override
            public void failedToReceiveAd(int i)
            {
                Toast.makeText( getActivity(), "Rewarded video load failed: " + i, Toast.LENGTH_SHORT ).show();
                mVideoReady = false;
            }
        } );
    }

    private void showRewardedVideo()
    {
        if ( mVideoReady )
        {
            mVideoReady = false;
            mInterstitial.show( getActivity(), this, this, this, this );
        }
        else
        {
            Toast.makeText( getActivity(), "No rewarded video is ready. Please oad one first.", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void userRewardVerified(AppLovinAd appLovinAd, Map map)
    {
        final String amount = (String) map.get( "amount" );
        final String currency = (String) map.get( "currency" );

        Toast.makeText( getActivity(), "Rewarded verified: " + amount + " " + currency, Toast.LENGTH_LONG ).show();

        mCoinsEarned += Float.parseFloat( amount );
        mCoinsText.setText( "Your Coins: " + mCoinsEarned );
    }

    @Override
    public void userOverQuota(AppLovinAd appLovinAd, Map map)
    {
        Toast.makeText( getActivity(), "Already earned the maximum reward for today.", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void userRewardRejected(AppLovinAd appLovinAd, Map map)
    {
        Toast.makeText( getActivity(), "Couldn't verify your reward. Is Ad Tracking disabled?", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void validationRequestFailed(AppLovinAd appLovinAd, int errorCode)
    {
        if ( errorCode == AppLovinErrorCodes.USER_CLOSED_VIDEO )
        {
            // Your user exited the video prematurely. It's up to you if you'd still like to grant
            // a reward in this case. Most developers choose not to. Note that this case can occur
            // after a reward was initially granted (since reward validation happens as soon as a
            // video is launched).

            Toast.makeText( getActivity(), "Video exited early.", Toast.LENGTH_LONG ).show();
        }
        else
        {
            Toast.makeText( getActivity(), "Server issue while validating reward. Try again later.", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void userDeclinedToViewAd(AppLovinAd appLovinAd)
    {
        Toast.makeText( getActivity(), "You chose not to view a rewarded video.", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd)
    {
        Toast.makeText( getActivity(), "Started playing a video ad...", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double v, boolean b)
    {
        Toast.makeText( getActivity(), "Finished playing a video ad", Toast.LENGTH_SHORT ).show();
    }

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

    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        Toast.makeText( getActivity(), "Interstitial clicked.", Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void recreateFragment()
    {

    }
}
