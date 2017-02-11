package com.example.android.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/2/8.
 */

public class LoadImageAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public LoadImageAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.list_item_movie, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public void clear() {
        super.clear();
    }

    //更新数据
    public void addMovie(String[] movieUrls) {
        imageUrls = movieUrls;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {
            gridView =  inflater.inflate(R.layout.list_item_movie, parent, false);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.list_item_movie_imageview);
            Picasso
                    .with(context)
                    .load(imageUrls[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .into(imageView);
        }else {
            gridView = convertView;
        }
        return gridView;
    }
}
