package com.example.android.project1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MaoyanFragment extends Fragment {

    private MaoyanAdapter maoyanAdapter;
    private URL url;
    private int screenWidth;

    public MaoyanFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maoyan, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclerView_maoyan);

        //获取屏幕高度和宽度
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
        //screenHeight = metric.heightPixels;   // 屏幕高度（像素）

        StaggeredGridLayoutManager mgr = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        maoyanAdapter = new MaoyanAdapter(getActivity(), screenWidth);
        recyclerView.setAdapter(maoyanAdapter);
        return rootView;
    }

    public void updateMovie() {
        Uri builtUrl;
        final String MOVIE_BASE_URL = "http://m.maoyan.com/movie/list.json?type=hot&offset=0";
        final String LIMIT = "limit";
        builtUrl = Uri.parse(MOVIE_BASE_URL).buildUpon().appendQueryParameter(LIMIT, "36")
                .build();
        try {
            url = new URL(builtUrl.toString());
        } catch (IOException e) {
            Log.d("Error", "URL格式错误");
        }
        FetchMaoyanTask maoyanTask = new FetchMaoyanTask(getActivity(), new FetchMyMaoyanTaskCompleteListener());
        maoyanTask.execute(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMyMaoyanTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<MaoyanParcelable>> {

        @Override
        public void onTaskCpmplete(ArrayList<MaoyanParcelable> result) {
            if (result != null) {
                maoyanAdapter.add(result);
            }
        }
    }

    // 实现数据传递
    public void getString(Callback callback) {
        String msg = "猫眼电影";
        callback.getString(msg);
    }
}
