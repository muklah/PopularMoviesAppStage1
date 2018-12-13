package com.example.muklahhn.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.muklahhn.popular_movies.utilities.NetworkUtils;
import com.example.muklahhn.popular_movies.utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    RecyclerViewAdapter mAdapter;
    RecyclerView mMoviesList;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    String sortOrder="popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager LayoutManager = new GridLayoutManager(this, 2);

        mMoviesList.setLayoutManager(LayoutManager);

        mMoviesList.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(this);

        mMoviesList.setAdapter(mAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMoviesData(sortOrder);
    }

    private void loadMoviesData(String sortOrder) {
        showMovieDataView();
        new FetchMoviesTask().execute(sortOrder);
    }

    @Override
    public void onClick(MovieItem movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", movie);
        startActivity(intentToStartDetailActivity);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMoviesList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMoviesList.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<MovieItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String sortOrder = params[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(sortOrder);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);

                ArrayList<MovieItem> simpleJsonMovieData = OpenMovieJsonUtils.getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                    mAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.sort, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.most_popular) {
                String sortOrder="popular";
                loadMoviesData(sortOrder);
                return true;
            }

            if (id == R.id.top_rated) {
                String sortOrder="top_rated";
                loadMoviesData(sortOrder);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }