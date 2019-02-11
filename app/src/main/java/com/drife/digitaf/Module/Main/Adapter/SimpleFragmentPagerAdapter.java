package com.drife.digitaf.Module.Main.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private List<Integer> listIcon;
    private Context context;

    private List<Integer> listPage = new ArrayList();

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        listFragment = new ArrayList<>();
        listTitle = new ArrayList<>();
        listIcon = new ArrayList<>();
        this.context = context;
    }

    public void addTab(Fragment fragment, String title, int icon) {
        listFragment.add(fragment);
        listTitle.add(title);
        listIcon.add(icon);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_main, null);

        TextView tabTextView = view.findViewById(R.id.tvTitle);
        tabTextView.setText(listTitle.get(position));

        ImageView imageView = view.findViewById(R.id.ivIcon);
        imageView.setImageDrawable(context.getResources().getDrawable(listIcon.get(position)));

        return view;
    }

    public void forward(int pos) {
        listPage.add(pos);
    }

    public int back() {
        int last = 0;

        if (listPage.size() > 1) {
            last = listPage.get(listPage.size()-2);
            listPage.remove(listPage.size()-1);
            listPage.remove(listPage.size()-1);
        }

        return last;
    }
}
