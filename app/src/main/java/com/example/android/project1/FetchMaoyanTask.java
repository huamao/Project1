package com.example.android.project1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FetchMaoyanTask extends AsyncTask<Object, Void, ArrayList<MaoyanParcelable>> {

    private final String LOG_TAG = FetchMaoyanTask.class.getSimpleName();

    private ProgressDialog progressDialog;

    private Context context;

    private ArrayList<MaoyanParcelable> dataList = new ArrayList<MaoyanParcelable>();

    private ArrayList<MaoyanParcelable> dataList_new = new ArrayList<MaoyanParcelable>();

    private AsyncTaskCompleteListener<ArrayList<MaoyanParcelable>> listener;

    public FetchMaoyanTask(Context context, AsyncTaskCompleteListener<ArrayList<MaoyanParcelable>> listener) {
        this.context = context;
        this.listener = listener;
    }

    private ArrayList<MaoyanParcelable> getMaoyanDataFromJson(String maoyanJsonStr) throws JSONException {

        final String DATA = "data";

        final String RESULT = "movies";

        final String ID = "id";

        final String IMG = "img";

        JSONObject maoyanJson = new JSONObject(maoyanJsonStr);

        JSONObject maoyanJson_data = maoyanJson.getJSONObject(DATA);

        JSONArray maoyanArray = maoyanJson_data.getJSONArray(RESULT);

        for (int i = 0; i < maoyanArray.length(); i++) {
            MaoyanParcelable maoyanParcelable = new MaoyanParcelable(Parcel.obtain());
            //电影id
            String id;
            //电影图片
            String img_path;

            JSONObject onceMovie = maoyanArray.getJSONObject(i);

            id = onceMovie.getString(ID);

            maoyanParcelable.setMaoyan_id(id);

            img_path = onceMovie.getString(IMG);

            maoyanParcelable.setMaoyan_img(img_path);

            dataList.add(maoyanParcelable);
        }
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
    protected ArrayList<MaoyanParcelable> doInBackground(Object... params) {
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
            getMaoyanMovies(getMaoyanDataFromJson(movieJsonStr));
            //return getMaoyanDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return dataList_new;
    }

    private ArrayList<MaoyanParcelable> getMaoyanMovies(ArrayList<MaoyanParcelable> dataResult) {
        if (dataResult.size() == 0) {
            return null;
        }
        URL url = null;
        for (int i = 0; i < dataResult.size(); i++) {
            Uri builtUrl;
            String MOVIE_BASE_URL = "http://m.maoyan.com/movie/" + dataResult.get(i).getMaoyan_id() + ".json";
            builtUrl = Uri.parse(MOVIE_BASE_URL).buildUpon().build();
            try {
                url = new URL(builtUrl.toString());
            } catch (IOException e) {
                Log.d("Error", "URL格式错误");
            }


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
                getMaoyanMoviesDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return dataList_new;
    }

    private void getMaoyanMoviesDataFromJson(String maoyanJsonStr) throws JSONException {

        final String DATA = "data";

        final String RESULT = "MovieDetailModel";

        final String ID = "id";

        final String IMG = "img";

        final String NM = "nm";

        final String CAT = "cat";

        final String DRA = "dra";

        final String DIR = "dir";

        final String STAR = "star";

        final String RT = "rt";

        final String SC = "sc";

        final String WISH = "wish";

        final String VD = "vd";

        JSONObject maoyanJson = new JSONObject(maoyanJsonStr);

        JSONObject maoyanJson_data = maoyanJson.getJSONObject(DATA);

        JSONObject onceMovie = maoyanJson_data.getJSONObject(RESULT);

        MaoyanParcelable maoyanParcelable = new MaoyanParcelable(Parcel.obtain());
        //电影id
        String id;
        //电影名字
        String nm;
        //电影类型
        String cat;
        //电影详情描述文字
        String img_path;
        //电影简介
        String dra;
        //电影导演
        String dir;
        //电影演员
        String star;
        //发布日期
        String rt;
        //评分结果
        String sc;
        //期望看此电影的人数
        String wish;
        //电影预告片
        String vd;


        id = onceMovie.getString(ID);

        maoyanParcelable.setMaoyan_id(id);

        img_path = onceMovie.getString(IMG);

        maoyanParcelable.setMaoyan_img(img_path);

        nm = onceMovie.getString(NM);

        maoyanParcelable.setMaoyan_nm(nm);

        cat = onceMovie.getString(CAT);

        maoyanParcelable.setMaoyan_cat(cat);

        dra = ClearP(onceMovie.getString(DRA));

        maoyanParcelable.setMaoyan_dra(dra);

        dir = onceMovie.getString(DIR);

        maoyanParcelable.setMaoyan_dir(dir);

        star = onceMovie.getString(STAR);

        maoyanParcelable.setMaoyan_star(star);

        rt = onceMovie.getString(RT);

        maoyanParcelable.setMaoyan_rt(rt);

        sc = onceMovie.getString(SC);

        maoyanParcelable.setMaoyan_sc(sc);

        wish = onceMovie.getString(WISH);

        maoyanParcelable.setMaoyan_wish(wish);

        vd = onceMovie.getString(VD);

        maoyanParcelable.setMaoyan_vd(vd);

        dataList_new.add(maoyanParcelable);
    }

    /**
     * 去除首尾<p></p>
     * @param str
     * @return
     */
    private String ClearP(String str) {
        String regex="^<p>(.*)</p>$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), m.group(1));
        }
       return str;
    }

    @Override
    protected void onPostExecute(ArrayList<MaoyanParcelable> result) {
        super.onPostExecute(result);
        listener.onTaskCpmplete(result);
        progressDialog.dismiss();
    }
}
