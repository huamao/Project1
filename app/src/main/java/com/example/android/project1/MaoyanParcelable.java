package com.example.android.project1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/24.
 */

public class MaoyanParcelable implements Parcelable {

    //电影id，通过此id可以查看电影详细信息
    private String maoyan_id;
    //电影海报
    private String maoyan_img;
    //电影名字
    private String maoyan_nm;
    //电影类型
    private String maoyan_cat;
    //电影简介
    private String maoyan_dra;
    //电影导演
    private String maoyan_dir;
    //电影主演
    private String maoyan_star;
    //电影上映时间
    private String maoyan_rt;
    //电影评分
    private String maoyan_sc;
    //想观看电影人数
    private String maoyan_wish;
    //电影预告mp4影片
    private String maoyan_vd;

    protected MaoyanParcelable(Parcel in) {
        maoyan_id = in.readString();
        maoyan_img = in.readString();
        maoyan_nm = in.readString();
        maoyan_cat = in.readString();
        maoyan_dra = in.readString();
        maoyan_dir = in.readString();
        maoyan_star = in.readString();
        maoyan_rt = in.readString();
        maoyan_sc = in.readString();
        maoyan_wish = in.readString();
        maoyan_vd = in.readString();
    }

    public static final Creator<MaoyanParcelable> CREATOR = new Creator<MaoyanParcelable>() {
        @Override
        public MaoyanParcelable createFromParcel(Parcel in) {
            return new MaoyanParcelable(in);
        }

        @Override
        public MaoyanParcelable[] newArray(int size) {
            return new MaoyanParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maoyan_id);
        dest.writeString(maoyan_img);
        dest.writeString(maoyan_nm);
        dest.writeString(maoyan_cat);
        dest.writeString(maoyan_dra);
        dest.writeString(maoyan_dir);
        dest.writeString(maoyan_star);
        dest.writeString(maoyan_rt);
        dest.writeString(maoyan_sc);
        dest.writeString(maoyan_wish);
        dest.writeString(maoyan_vd);
    }

    public String getMaoyan_id() {
        return maoyan_id;
    }

    public void setMaoyan_id(String maoyan_id) {
        this.maoyan_id = maoyan_id;
    }

    public String getMaoyan_img() {
        return maoyan_img;
    }

    public void setMaoyan_img(String maoyan_img) {
        this.maoyan_img = maoyan_img;
    }

    public String getMaoyan_nm() {
        return maoyan_nm;
    }

    public void setMaoyan_nm(String maoyan_nm) {
        this.maoyan_nm = maoyan_nm;
    }

    public String getMaoyan_cat() {
        return maoyan_cat;
    }

    public void setMaoyan_cat(String maoyan_cat) {
        this.maoyan_cat = maoyan_cat;
    }

    public String getMaoyan_dra() {
        return maoyan_dra;
    }

    public void setMaoyan_dra(String maoyan_dra) {
        this.maoyan_dra = maoyan_dra;
    }

    public String getMaoyan_dir() {
        return maoyan_dir;
    }

    public void setMaoyan_dir(String maoyan_dir) {
        this.maoyan_dir = maoyan_dir;
    }

    public String getMaoyan_star() {
        return maoyan_star;
    }

    public void setMaoyan_star(String maoyan_star) {
        this.maoyan_star = maoyan_star;
    }

    public String getMaoyan_rt() {
        return maoyan_rt;
    }

    public void setMaoyan_rt(String maoyan_rt) {
        this.maoyan_rt = maoyan_rt;
    }

    public String getMaoyan_sc() {
        return maoyan_sc;
    }

    public void setMaoyan_sc(String maoyan_sc) {
        this.maoyan_sc = maoyan_sc;
    }

    public String getMaoyan_wish() {
        return maoyan_wish;
    }

    public void setMaoyan_wish(String maoyan_wish) {
        this.maoyan_wish = maoyan_wish;
    }

    public String getMaoyan_vd() {
        return maoyan_vd;
    }

    public void setMaoyan_vd(String maoyan_vd) {
        this.maoyan_vd = maoyan_vd;
    }

    public MaoyanParcelable(String maoyan_id, String maoyan_img, String maoyan_nm, String maoyan_cat, String maoyan_dra,String maoyan_dir,  String maoyan_star, String maoyan_rt, String maoyan_sc, String maoyan_wish, String maoyan_vd) {
        this.maoyan_id = maoyan_id;
        this.maoyan_img = maoyan_img;
        this.maoyan_nm = maoyan_nm;
        this.maoyan_cat = maoyan_cat;
        this.maoyan_dra = maoyan_dra;
        this.maoyan_dir = maoyan_dir;
        this.maoyan_star = maoyan_star;
        this.maoyan_rt = maoyan_rt;
        this.maoyan_sc = maoyan_sc;
        this.maoyan_wish = maoyan_wish;
        this.maoyan_vd = maoyan_vd;
    }
}
