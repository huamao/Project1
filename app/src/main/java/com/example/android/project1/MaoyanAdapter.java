package com.example.android.project1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/24.
 */

public class MaoyanAdapter extends RecyclerView.Adapter<MaoyanAdapter.ViewHolder> {

    private int screenWidth;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MaoyanParcelable> maoyanParcelables;
    private MaoyanParcelable maoyanParcelable;

    public MaoyanAdapter(Context context, int screenWidth) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.screenWidth = screenWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_movie, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //调整图片宽度，高度
        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
        lp.width = screenWidth / 2;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.imageView.setLayoutParams(lp);
        holder.imageView.setMaxWidth(screenWidth / 2);//这里除以2是因为有2列，所以宽度是屏幕宽度的1/2
        holder.imageView.setMaxHeight((int) ((screenWidth / 2) * 1.4));// 这里设置高度为最大宽度的1.4倍
        if(maoyanParcelables != null) {
            Picasso
                    .with(context)
                    .load(maoyanParcelables.get(position).getMaoyan_img())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (maoyanParcelables == null) {
        return 4;
        }
        return maoyanParcelables.size();
    }

    /**
     * 更新数据
     * @param maoyanParcelables
     */
    public void add(ArrayList<MaoyanParcelable> maoyanParcelables) {
        this.maoyanParcelables = maoyanParcelables;
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
