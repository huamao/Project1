package com.example.android.project1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static android.widget.ImageView.ScaleType.FIT_START;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private int screenWidth;
    private String[] data;
    private Context context;
    private LayoutInflater inflater;
    private String[] movieTitleStr;
    private String[] backgroundPicStr;
    private String[] detailsStr;
    private String[] datesStr;
    private String[] vote_averagesStr;

    public ImageAdapter(Context context, String[] imageUrls, int screenWidth) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.screenWidth = screenWidth;
        data = imageUrls;
    }

    //RecyclerView显示的子View
    //该方法返回是ViewHolder，当有可复用View时，就不再调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_movie, null);
        return new ViewHolder(view);
    }

    //将数据绑定到子View
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //调整图片宽度，高度
        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
        lp.width = screenWidth / 2;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.imageView.setLayoutParams(lp);
        holder.imageView.setMaxWidth(screenWidth / 2);//这里除以2是因为有2列，所以宽度是屏幕宽度的1/2
        holder.imageView.setMaxHeight((int) ((screenWidth / 2) * 1.4));// 这里设置高度为最大宽度的1.4倍
        Picasso
                .with(context)
                .load(data[position])
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bundle bundleToDetail = new Bundle();
                bundleToDetail.putString("backgroundPicStr", data[position]);
                bundleToDetail.putString("jsonData", movieTitleStr[position]);
                bundleToDetail.putString("detailsStr", detailsStr[position]);
                bundleToDetail.putString("datesStr", datesStr[position]);
                bundleToDetail.putString("vote_averagesStr", vote_averagesStr[position]);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtras(bundleToDetail);
                context.startActivity(intent);
            }
        });
    }

    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {
        return data.length;
    }

    //更新数据
    public void add(String[] strs, String[] movieTitle, String[] backgroundPic, String[] details, String[] dates, String[] vote_averages) {
        data = strs;
        movieTitleStr = movieTitle;
        backgroundPicStr = backgroundPic;
        detailsStr = details;
        datesStr = dates;
        vote_averagesStr = vote_averages;
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
