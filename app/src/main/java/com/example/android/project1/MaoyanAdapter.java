package com.example.android.project1;

import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                maoyanParcelable = new MaoyanParcelable(maoyanParcelables.get(position).getMaoyan_id(), maoyanParcelables.get(position).getMaoyan_img(), maoyanParcelables.get(position).getMaoyan_nm(),
                        maoyanParcelables.get(position).getMaoyan_cat(), maoyanParcelables.get(position).getMaoyan_dra(), maoyanParcelables.get(position).getMaoyan_dir(), maoyanParcelables.get(position).getMaoyan_star(),
                        maoyanParcelables.get(position).getMaoyan_rt(),
                        maoyanParcelables.get(position).getMaoyan_sc(), maoyanParcelables.get(position).getMaoyan_wish(),maoyanParcelables.get(position).getMaoyan_vd());
                Intent intent = new Intent(context, MaoyanDetailActivity.class);
                intent.putParcelableArrayListExtra("movies", maoyanParcelables);
                intent.putExtra("movie", maoyanParcelable);
                intent.putExtra("clickMoviePosition", position);
                intent.putExtra("name", "猫眼电影详情");
                context.startActivity(intent);
            }
        });
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
