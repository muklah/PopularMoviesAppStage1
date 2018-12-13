package com.example.muklahhn.popular_movies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView mMoviePoster;
    private TextView mMovieName;
    private TextView mMovieDate;
    private TextView mVoteAverage;
    private TextView mMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMoviePoster = (ImageView) findViewById(R.id.iv_poster);
        mMovieName = (TextView) findViewById(R.id.tv_movie_name);
        mMovieDate = (TextView) findViewById(R.id.tv_movie_date);
        mVoteAverage = (TextView) findViewById(R.id.tv_vote_average);
        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);

        MovieItem movieObject = getIntent().getParcelableExtra("movie");

        if (movieObject != null) {
            Picasso.with(this)
                    .load(movieObject.getFullPosterPath())
                    .placeholder(R.drawable.three)
                    .resize(700,1000)
                    .into(mMoviePoster);
            mMovieName.setText(String.valueOf(movieObject.getName()));
            mMovieDate.setText(String.valueOf(movieObject.getDate()));
            mVoteAverage.setText(String.valueOf(movieObject.getAverage()));
            mMovieOverview.setText(String.valueOf(movieObject.getDescription()));
        }
    }
}
