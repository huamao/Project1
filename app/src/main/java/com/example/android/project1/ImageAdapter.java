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
 * Created by Administrator on 2017/2/9.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private int screenWidth;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MovieParcelable> movieParcelables;
    private MovieParcelable movieParcelable;

    public ImageAdapter(Context context, int screenWidth) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.screenWidth = screenWidth;
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
        if(movieParcelables != null) {
            Picasso
                    .with(context)
                    .load(movieParcelables.get(position).getThemoviedb_backgroundPic())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                movieParcelable = new MovieParcelable(movieParcelables.get(position).getThemoviedb_backgroundPic(), movieParcelables.get(position).getThemoviedb_title(),
                        movieParcelables.get(position).getThemoviedb_detail(), movieParcelables.get(position).getThemoviedb_date(), movieParcelables.get(position).getThemoviedb_vote_average());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putParcelableArrayListExtra("movies", movieParcelables);
                intent.putExtra("movie", movieParcelable);
                intent.putExtra("clickMoviePosition", position);
                intent.putExtra("name", "themoviedb热门电影详情");
                context.startActivity(intent);
            }
        });
    }

    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {
        if (movieParcelables == null) {
            return 4;
        }
        return movieParcelables.size();
    }

    /**
     * 更新数据
     * @param movieParcelables
     */
    public void add(ArrayList<MovieParcelable> movieParcelables) {
        this.movieParcelables = movieParcelables;
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
