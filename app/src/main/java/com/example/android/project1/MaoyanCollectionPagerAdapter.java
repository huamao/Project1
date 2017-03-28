package com.example.android.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by wangxq on 2017/3/27.
 */

public class MaoyanCollectionPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<MaoyanParcelable> listMovies;

    public MaoyanCollectionPagerAdapter(FragmentManager fm, ArrayList<MaoyanParcelable> listMovies) {
        super(fm);
        this.listMovies = listMovies;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MaoyanDetailActivity.MaoyanDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("Movie",listMovies.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        if (listMovies == null) {
            return 1;
        }
        return listMovies.size();
    }
}
