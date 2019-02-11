package com.drife.digitaf.Module.OnBoardActivity.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.drife.digitaf.Module.OnBoardActivity.Fragment.OnBoardFragment;

public class OnBoardAdapter extends FragmentPagerAdapter {
    Context context;

    public OnBoardAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        fragment = new OnBoardFragment();
        ((OnBoardFragment) fragment).pos = i;

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
