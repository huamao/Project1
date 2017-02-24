package com.example.android.project1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/4.
 */

public class MovieFragment extends Fragment {
    private ImageAdapter mMovieAdapter;
    private URL url;

    private int screenWidth;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_fragment_main, container, false);
        RecyclerView recylcer = (RecyclerView) rootView.findViewById(R.id.RecyclerView_movie);

        //获取屏幕高度和宽度
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
        //screenHeight = metric.heightPixels;   // 屏幕高度（像素）

        StaggeredGridLayoutManager mgr = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recylcer.setLayoutManager(mgr);
        mMovieAdapter = new ImageAdapter(getActivity(), screenWidth);
        recylcer.setAdapter(mMovieAdapter);
        return rootView;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovie();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateMovie() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movie_type = prefs.getString(getString(R.string.pref_movie_type_key), getString(R.string.pref_movie_value_popular));
        Uri builtUrl;
        if (movie_type.equals("popular")) {
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?language=zh";
            final  String APPID_PARAM = "api_key";
            builtUrl = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_API_KEY)
                    .build();
            try {
                url = new URL(builtUrl.toString());
            } catch (IOException e) {
                Log.d("Error", "URL格式错误");
            }
        } else if (movie_type.equals("top_rated")) {
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?language=zh";
            final  String APPID_PARAM = "api_key";
            builtUrl = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_API_KEY)
                    .build();
            try {
                url = new URL(builtUrl.toString());
            } catch (IOException e) {
                Log.d("Error", "URL格式错误");
            }
        }
        FetchMovieTask movieTask = new FetchMovieTask(getActivity(), new FetchMyMovieTaskCompleteListener());
        movieTask.execute(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMyMovieTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<MovieParcelable>> {

        @Override
        public void onTaskCpmplete(ArrayList<MovieParcelable> result) {
            if (result != null) {
                mMovieAdapter.add(result);
            }
        }
    }
}
