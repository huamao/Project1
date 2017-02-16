package com.example.android.project1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MovieParcelable implements Parcelable {
    private String backgroundPic;

    private String title;

    private String detail;

    private String date;

    private String vote_average;

    protected MovieParcelable(Parcel in) {
        backgroundPic = in.readString();
        title = in.readString();
        detail = in.readString();
        date = in.readString();
        vote_average = in.readString();
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
        dest.writeString(backgroundPic);
        dest.writeString(title);
        dest.writeString(detail);
        dest.writeString(date);
        dest.writeString(vote_average);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBackgroundPic() {
        return backgroundPic;
    }

    public void setBackgroundPic(String backgroundPic) {
        this.backgroundPic = backgroundPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String movieTitle) {
        this.title = movieTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public MovieParcelable(String backgroundPic, String title, String detail, String date, String vote_average) {
        this.backgroundPic = backgroundPic;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.vote_average = vote_average;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "backgroundPic='" + backgroundPic + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", date='" + date + '\'' +
                ", vote_average='" + vote_average + '\'' +
                '}';
    }
}
