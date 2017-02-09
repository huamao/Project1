package com.example.android.project1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/4.
 */

public class MovieFragment extends Fragment {

    private LoadImageAdapter mMovieAdapter;

    //private String[] mMovieStrs;

    private URL url;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

   private String[] mMovieStrs = {"https://image.tmdb.org/t/p/w185/AgzX7mmCrQcSozvqWGwSpFAsEXj.jpg",
            "https://image.tmdb.org/t/p/w185/4W9ejAfUnqbBAD5wmfGxC04wFp8.jpg",
            "https://image.tmdb.org/t/p/w185/tBrssQ4PksKC5sBBWOITyyDgflZ.jpg",
            "https://image.tmdb.org/t/p/w185/aHhGjO3jaxUKiWXiXwJCVt3icjC.jpg",
            "https://image.tmdb.org/t/p/w185/adqL6JyooWGK6xyHkWPjVO4rI7b.jpg",
            "https://image.tmdb.org/t/p/w185/6YRdgYyT6qZHcRhR6qKedY3qS7R.jpg",
            "https://image.tmdb.org/t/p/w185/sog24e3ZoiztJ3QEdynCM0SPsVy.jpg",
            "https://image.tmdb.org/t/p/w185/9bBogMZ9WfA37BjReVk7FU78eRn.jpg"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.listview_movie);
        //mMovieStrs = new String[20];
        mMovieAdapter = new LoadImageAdapter(getActivity(), mMovieStrs);
        gridView.setAdapter(mMovieAdapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
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

    private void updateMovie() {
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
        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    public class FetchMovieTask extends AsyncTask<URL, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private String[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {
            final int number = 20;
            // These are the names of the JSON objects that need to be extracted.
            final String RESULT = "results";
            //final String TITLE = "title";
            final String PICTURE_PATH = "poster_path";
            //final String DETAILS = "overview";
            //final String DATE = "release_date";
            //final String BACKDROP_PATH = "backdrop_path";
            //final String VOTE_AVERAGE = "vote_average";

            JSONObject forecastJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = forecastJson.getJSONArray(RESULT);

            String[] resultStrs = new String[number];
            for(int i = 0; i < movieArray.length(); i++) {
                //电影名字
                //String title;
                //电影详情描述文字
                //String description;
                //电影图片
                String poster_path;
                //发布日期
                //String release_date;
                //背景图片
                //String backdrop_path;
                //投票结果
                //String vote_average;

                JSONObject onceMovie = movieArray.getJSONObject(i);
                String tmp = onceMovie.getString(PICTURE_PATH);
                poster_path = "https://image.tmdb.org/t/p/w185" + tmp;
                resultStrs[i] = poster_path;
            }
            return resultStrs;
        }

        @Override
        protected String[] doInBackground(URL... params) {
            if (params.length == 0) {
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            try {
                urlConnection = (HttpURLConnection) params[0].openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return  null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
                // Log.v(LOG_TAG, "JSON:" + forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                //mMovieAdapter.addMovie(result);
                //for (String onceMovieStr : result) {
                //    mMovieAdapter.add(onceMovieStr);
                }
            }
        }
    }
