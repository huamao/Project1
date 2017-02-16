package com.example.android.project1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class FetchMovieTask extends AsyncTask<Object, Void, List<String[]>> {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private ProgressDialog progressDialog;

    private Context context;

    private AsyncTaskCompleteListener<List<String[]>> listener;

    public FetchMovieTask(Context context, AsyncTaskCompleteListener<List<String[]>> listener) {
        this.context = context;
        this.listener = listener;
    }

    private List<String[]> dataList = new ArrayList<String[]>();

    //private ImageAdapter imageAdapter;

    private List<String[]> getMovieDataFromJson(String movieJsonStr)
            throws JSONException {
        final int number = 20;
        // These are the names of the JSON objects that need to be extracted.
        final String RESULT = "results";
        final String TITLE = "title";
        final String PICTURE_PATH = "poster_path";
        final String DETAILS = "overview";
        final String DATE = "release_date";
        final String BACKDROP_PATH = "backdrop_path";
        final String VOTE_AVERAGE = "vote_average";

        JSONObject forecastJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = forecastJson.getJSONArray(RESULT);

        String[] resultStrs = new String[number];
        String[] titleStrs = new String[number];
        String[] detailStrs = new String[number];
        String[] dateStrs = new String[number];
        String[] backPicStrs = new String[number];
        String[] vote_averageStrs = new String[number];
        for (int i = 0; i < movieArray.length(); i++) {
            //电影名字
            String title;
            //电影详情描述文字
            String description;
            //电影图片
            String poster_path;
            //发布日期
            String release_date;
            //背景图片
            String backdrop_path;
            //评分结果
            String vote_average;

            JSONObject onceMovie = movieArray.getJSONObject(i);
            String tmp_p = onceMovie.getString(PICTURE_PATH);
            poster_path = "https://image.tmdb.org/t/p/w185" + tmp_p;
            resultStrs[i] = poster_path;
            title = onceMovie.getString(TITLE);
            titleStrs[i] = title;
            description = onceMovie.getString(DETAILS);
            detailStrs[i] = description;
            release_date = onceMovie.getString(DATE);
            dateStrs[i] = release_date;
            String tmp_b = onceMovie.getString(BACKDROP_PATH);
            backdrop_path = "https://image.tmdb.org/t/p/w185" + tmp_b;
            backPicStrs[i] = backdrop_path;
            vote_average = onceMovie.getString(VOTE_AVERAGE);
            vote_averageStrs[i] = vote_average;
        }
        dataList.add(resultStrs);
        dataList.add(titleStrs);
        dataList.add(detailStrs);
        dataList.add(dateStrs);
        dataList.add(backPicStrs);
        dataList.add(vote_averageStrs);
        return dataList;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("电影信息");
        progressDialog.setMessage("加载中，请稍等...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<String[]> doInBackground(Object... params) {
        if (params.length == 0) {
            return null;
        }
        URL url = (URL) params[0];
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
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
    protected void onPostExecute(List<String[]> result) {
        super.onPostExecute(result);
        listener.onTaskCpmplete(result);
        progressDialog.dismiss();
    }
}
