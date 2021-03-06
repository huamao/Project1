package com.example.android.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import static com.example.android.project1.R.id.container;

/**
 * Created by Administrator on 2017/2/4.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
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
        return  super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {
        private String mMovieStr;
        private String backgroundPicStr;
        private String detailsStr;
        private String DateStr;
        private String vote_averageStr;
        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_background);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                backgroundPicStr = intent.getStringExtra("backgroundPicStr");
                mMovieStr = intent.getStringExtra("jsonData");
                detailsStr = intent.getStringExtra("detailsStr");
                DateStr = intent.getStringExtra("datesStr");
                vote_averageStr = intent.getStringExtra("vote_averagesStr");
                Picasso
                        .with(getActivity())
                        .load(backgroundPicStr)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                ((TextView) rootView.findViewById(R.id.detail_date)).setText(DateStr);
                ((TextView) rootView.findViewById(R.id.vote_average)).setText(vote_averageStr + "/10");
                ((TextView) rootView.findViewById(R.id.title_text)).setText(mMovieStr);
                ((TextView) rootView.findViewById(R.id.name_text)).setText("电影详情：");
                TextView tv = (TextView) rootView.findViewById(R.id.detail_text);
                tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                tv.setText(detailsStr);
            }
            return rootView;
            }
        }
    }

