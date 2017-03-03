package com.example.android.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static com.example.android.project1.R.id.container;

/**
 * Created by Administrator on 2017/2/4.
 */

public class DetailActivity extends AppCompatActivity {

    MovieCollectionPagerAdapter movieCollectionPagerAdapter;
    private ArrayList<MovieParcelable> movieParcelables;
    private static ViewPager mViewPager;
    private int checkPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        movieParcelables = intent.getParcelableArrayListExtra("movies");
        checkPosition = intent.getIntExtra("clickMoviePosition", 0);
        movieCollectionPagerAdapter = new MovieCollectionPagerAdapter(getSupportFragmentManager(), movieParcelables);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(movieCollectionPagerAdapter);
        mViewPager.setCurrentItem(checkPosition);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        private MovieParcelable movieParcelable;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_background);
            Bundle args = getArguments();
            Intent intent = getActivity().getIntent();
            if (intent != null && args != null) {
                movieParcelable = args.getParcelable("Movie");
            } else {
                movieParcelable = intent.getParcelableExtra("movie");//获取数据
            }
                Picasso
                        .with(getActivity())
                        .load(movieParcelable.getThemoviedb_backgroundPic())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                ((TextView) rootView.findViewById(R.id.detail_date)).setText(movieParcelable.getThemoviedb_date());
                ((TextView) rootView.findViewById(R.id.vote_average)).setText(movieParcelable.getThemoviedb_vote_average() + "/10");
                ((TextView) rootView.findViewById(R.id.title_text)).setText(movieParcelable.getThemoviedb_title());
                ((TextView) rootView.findViewById(R.id.name_text)).setText("电影详情：");
                ((TextView) rootView.findViewById(R.id.name_text)).getPaint().setFakeBoldText(true);
                TextView tv = (TextView) rootView.findViewById(R.id.detail_text);
                tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                tv.setText(movieParcelable.getThemoviedb_detail());

            return rootView;
        }
    }
}

