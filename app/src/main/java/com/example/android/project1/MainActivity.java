package com.example.android.project1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MovieFragment movieFragment = new MovieFragment();

    MaoyanFragment maoyanFragment = new MaoyanFragment();

    private String FILM_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maoyanFragment.getString(new Callback() {
            @Override
            public void getString(String msg) {
                FILM_TITLE = msg;
            }
        });

        //自定义toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(FILM_TITLE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_white_24dp);
        //设置NavigationIcon导航图标的返回事件

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //设置启动后Item的选中状态
        navigationView.getMenu().getItem(0).setChecked(true);

        if (savedInstanceState == null && isOnline()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, maoyanFragment)
                    .commit();
        } else {
            Toast.makeText(getApplicationContext(), R.string.app_network_status, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.moviefragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_refresh && isOnline()) {
            movieFragment.updateMovie();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), R.string.app_network_status, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, maoyanFragment)
                    .commit();
        } else if (id == R.id.nav_gallery) {
            if (movieFragment.isVisible()) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            Bundle bundle = new Bundle();
            bundle.putString("movie_name", "themoviedb电影");
            movieFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, movieFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
