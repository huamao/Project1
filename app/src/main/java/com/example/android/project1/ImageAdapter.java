package com.example.android.project1;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ImageAdapter extends RecyclerView.Adapter {

    private String[] data;
    private  Context context;
    private  LayoutInflater inflater;

    public ImageAdapter (Context context, String[] imageUrls) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        data = imageUrls;
    }

    //RecyclerView显示的子View
    //该方法返回是ViewHolder，当有可复用View时，就不再调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_movie, null);
        return new ViewHolder(view);
    }

    //将数据绑定到子View
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View gridView =  inflater.inflate(R.layout.list_item_movie, null);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.list_item_movie_imageview);
        Picasso
                .with(context)
                .load(data[position])
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(imageView);
    }

    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {
        return data.length;
    }

    //更新数据
    public void add(String[] strs) {
        data = strs;
        this.notifyDataSetChanged();
    }

    //自定义的ViewHolder,减少findViewById调用次数
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        //TODO  声明view
        public ViewHolder(View convertView) {
            //TODO 初始化view
            super(convertView);
            imageView = (ImageView) convertView.findViewById(R.id.list_item_movie_imageview);
        }
    }
}
