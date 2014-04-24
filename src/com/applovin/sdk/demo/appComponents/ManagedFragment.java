package com.applovin.sdk.demo.appComponents;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: mszaro Date: 4/23/14 Time: 12:19 PM
 */

/*
 * This class has nothing to do with the SDK, it's just a component used by the demo application. However, feel free to use this code in your own project if it's helpful to you.
 */
public abstract class ManagedFragment extends Fragment
{
    public static final int          NO_MENU     = -1;
    public volatile boolean          setUp       = false;

    private View                     mRootView;
    private Map<Integer, MenuAction> menuActions = new HashMap<Integer, MenuAction>( 0 );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mRootView = inflater.inflate( getLayoutId(), container, false );
        setHasOptionsMenu( getMenuId() != NO_MENU );
        assignViews();

        if ( !setUp )
        {
            setUp = true;
            setUpFragment();
        }

        recreateFragment();

        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        final int menuId = getMenuId();
        if ( menuId != NO_MENU )
        {
            inflater.inflate( menuId, menu );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int selectedId = item.getItemId();
        final MenuAction menuAction = menuActions.get( selectedId );

        if ( menuAction != null )
        {
            menuAction.onSelected();
            return true;
        }
        return false;
    }

    protected View findFragmentViewById(int id)
    {
        return mRootView.findViewById( id );
    }

    protected View getRootView()
    {
        return mRootView;
    }

    protected void addMenuAction(int menuItemId, MenuAction menuAction)
    {
        menuActions.put( menuItemId, menuAction );
    }

    protected abstract int getLayoutId();

    protected abstract int getMenuId();

    protected abstract void assignViews();

    protected abstract void setUpFragment();

    protected abstract void recreateFragment();
}
