package com.example.android.project1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MaoyanDetailActivity extends AppCompatActivity {

    MaoyanCollectionPagerAdapter maoyanCollectionPagerAdapter;
    private ArrayList<MaoyanParcelable> maoyanParcelables;
    private static ViewPager mViewPager;
    private int checkPosition;
    private String movie_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maoyan_detail);
        Intent intent = this.getIntent();
        maoyanParcelables = intent.getParcelableArrayListExtra("movies");
        checkPosition = intent.getIntExtra("clickMoviePosition", 0);
        movie_name = intent.getStringExtra("name");
        maoyanCollectionPagerAdapter = new MaoyanCollectionPagerAdapter(getSupportFragmentManager(), maoyanParcelables);
        //自定义toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_maoyan_detail);
        toolbar.setTitle(movie_name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        //设置NavigationIcon导航图标的返回事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.pager_maoyan);
        mViewPager.setAdapter(maoyanCollectionPagerAdapter);
        mViewPager.setCurrentItem(checkPosition);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_maoyan_detail, new MaoyanDetailActivity.MaoyanDetailFragment())
                    .commit();
        }
    }

    public static class MaoyanDetailFragment extends Fragment {
        private MaoyanParcelable maoyanParcelable;

        private VideoView videoView;

        public MaoyanDetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maoyan_detail, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_maoyan_background);
            Bundle args = getArguments();
            Intent intent = getActivity().getIntent();
            if (intent != null && args != null) {
                maoyanParcelable = args.getParcelable("Movie");
            } else {
                maoyanParcelable = intent.getParcelableExtra("movie");
            }
            Picasso
                    .with(getActivity())
                    .load(maoyanParcelable.getMaoyan_img())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            ((TextView) rootView.findViewById(R.id.title_text)).setText(maoyanParcelable.getMaoyan_nm());
            ((TextView) rootView.findViewById(R.id.detail_date)).setText(maoyanParcelable.getMaoyan_rt());
            ((TextView) rootView.findViewById(R.id.vote_average)).setText(maoyanParcelable.getMaoyan_sc() + "/10");
            ((TextView) rootView.findViewById(R.id.videoView_text)).setText("预告片：" + maoyanParcelable.getMaoyan_cat());
            ((TextView) rootView.findViewById(R.id.videoView_text)).getPaint().setFakeBoldText(true);
            videoView = (VideoView) rootView.findViewById(R.id.videoView);
            Uri uri = Uri.parse(maoyanParcelable.getMaoyan_vd());
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });
            ((TextView) rootView.findViewById(R.id.dir_text)).setText("导演：");
            ((TextView) rootView.findViewById(R.id.dir_text)).getPaint().setFakeBoldText(true);
            ((TextView) rootView.findViewById(R.id.dir)).setText(maoyanParcelable.getMaoyan_dir());
            ((TextView) rootView.findViewById(R.id.star_text)).setText("主演：");
            ((TextView) rootView.findViewById(R.id.star_text)).getPaint().setFakeBoldText(true);
            ((TextView) rootView.findViewById(R.id.star)).setText(maoyanParcelable.getMaoyan_star());
            ((TextView) rootView.findViewById(R.id.name_text)).setText("电影详情：");
            ((TextView) rootView.findViewById(R.id.name_text)).getPaint().setFakeBoldText(true);
            TextView tv = (TextView) rootView.findViewById(R.id.detail_text);
            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
            tv.setText(maoyanParcelable.getMaoyan_dra());

            return rootView;
        }
    }
}
