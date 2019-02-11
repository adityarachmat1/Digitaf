package com.drife.digitaf.Module.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.drife.digitaf.Module.myapplication.childfragment.DraftFragment;
import com.drife.digitaf.Module.myapplication.childfragment.NeedActionFragment;
import com.drife.digitaf.Module.myapplication.childfragment.OutboxFragment;
import com.drife.digitaf.R;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class MyApplicationTabAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] titles;
    private Fragment[] fragments;

    public MyApplicationTabAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;
        this.titles = context.getResources().getStringArray(R.array.myapplication_tabs);
        this.fragments = new Fragment[]{new DraftFragment(), new OutboxFragment(), new NeedActionFragment()};
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.r_custom_tab_myapplication, null);
        TextView tabTextView = view.findViewById(R.id.txtTabName);
        tabTextView.setText(titles[position]);
        return view;
    }
}
