package com.example.muklahhn.popular_movies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muklah H N on 15/09/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<MovieItem> mMoviesItems;
    private Context context;
    private final RecyclerViewAdapterOnClickHandler mClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(MovieItem movie);
    }

    public RecyclerViewAdapter(RecyclerViewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView MoviePopularity;
        public final ImageView MoviePoster;
        public final TextView MovieName;
        public final TextView MovieDate;
        public final TextView VoteAverage;
        public final TextView MovieOverview;

        public RecyclerViewHolder(View view) {
            super(view);
            MoviePopularity = (TextView)itemView.findViewById(R.id.movie_popularity);
            MoviePoster = (ImageView)itemView.findViewById(R.id.iv_item_movie);
            MovieName = (TextView)itemView.findViewById(R.id.movie_name);
            MovieDate = (TextView)itemView.findViewById(R.id.movie_date);
            VoteAverage = (TextView)itemView.findViewById(R.id.vote_average);
            MovieOverview = (TextView)itemView.findViewById(R.id.movie_overview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieItem movie = mMoviesItems.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.MoviePopularity.setText(String.valueOf(mMoviesItems.get(position).getPopularity()));
        Picasso.with(this.context).load(mMoviesItems.get(position).getFullPosterPath()).into(holder.MoviePoster);
        holder.MovieName.setText(String.valueOf(mMoviesItems.get(position).getName()));
    }


    @Override
    public int getItemCount() {
        if (null == mMoviesItems)
            return 0;
        else {
            return mMoviesItems.size();
        }
    }

    public void setMovieData(ArrayList<MovieItem> movieData) {
        mMoviesItems = movieData;
        notifyDataSetChanged();
    }

}