package com.example.android.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MovieCollectionPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<MovieParcelable> listMovies;

    public MovieCollectionPagerAdapter(FragmentManager fm, ArrayList<MovieParcelable> listMovies) {
        super(fm);
        this.listMovies = listMovies;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new DetailActivity.DetailFragment();
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
