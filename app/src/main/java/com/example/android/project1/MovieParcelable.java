package com.example.android.project1;

import android.os.Parcel;
import android.os.Parcelable;

import static com.example.android.project1.R.id.vote_average;
import static com.example.android.project1.R.menu.detail;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MovieParcelable implements Parcelable {
    private String themoviedb_backgroundPic;

    private String themoviedb_title;

    private String themoviedb_detail;

    private String themoviedb_date;

    private String themoviedb_vote_average;

    protected MovieParcelable(Parcel in) {
        themoviedb_backgroundPic = in.readString();
        themoviedb_title = in.readString();
        themoviedb_detail = in.readString();
        themoviedb_date = in.readString();
        themoviedb_vote_average = in.readString();
    }

    public static final Creator<MovieParcelable> CREATOR = new Creator<MovieParcelable>() {
        @Override
        public MovieParcelable createFromParcel(Parcel in) {
            return new MovieParcelable(in);
        }

        @Override
        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(themoviedb_backgroundPic);
        dest.writeString(themoviedb_title);
        dest.writeString(themoviedb_detail);
        dest.writeString(themoviedb_date);
        dest.writeString(themoviedb_vote_average);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getThemoviedb_backgroundPic() {
        return themoviedb_backgroundPic;
    }

    public void setThemoviedb_backgroundPic(String themoviedb_backgroundPic) {
        this.themoviedb_backgroundPic = themoviedb_backgroundPic;
    }

    public String getThemoviedb_title() {
        return themoviedb_title;
    }

    public void setThemoviedb_title(String themoviedb_title) {
        this.themoviedb_title = themoviedb_title;
    }

    public String getThemoviedb_detail() {
        return themoviedb_detail;
    }

    public void setThemoviedb_detail(String themoviedb_detail) {
        this.themoviedb_detail = themoviedb_detail;
    }

    public String getThemoviedb_date() {
        return themoviedb_date;
    }

    public void setThemoviedb_date(String themoviedb_date) {
        this.themoviedb_date = themoviedb_date;
    }

    public String getThemoviedb_vote_average() {
        return themoviedb_vote_average;
    }

    public void setThemoviedb_vote_average(String themoviedb_vote_average) {
        this.themoviedb_vote_average = themoviedb_vote_average;
    }

    public static Creator<MovieParcelable> getCREATOR() {
        return CREATOR;
    }

    public MovieParcelable(String themoviedb_backgroundPic, String themoviedb_title, String themoviedb_detail, String themoviedb_date, String themoviedb_vote_average) {
        this.themoviedb_backgroundPic = themoviedb_backgroundPic;
        this.themoviedb_title = themoviedb_title;
        this.themoviedb_detail = themoviedb_detail;
        this.themoviedb_date = themoviedb_date;
        this.themoviedb_vote_average = themoviedb_vote_average;
    }

}
