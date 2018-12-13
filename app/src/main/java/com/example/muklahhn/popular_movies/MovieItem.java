package com.example.muklahhn.popular_movies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Muklah H N on 15/09/2017.
 */

public class MovieItem implements Parcelable {

    private double popularity;
    private String photo;
    private String name;
    private String date;
    private double average;
    private String description;

    public MovieItem(double popularity, String poster_path, String original_title, String release_date, double vote_average, String overview) {
        this.popularity = popularity;
        this.photo = poster_path;
        this.name = original_title;
        this.date = release_date;
        this.average = vote_average;
        this.description = overview;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(popularity);
        out.writeString(photo);
        out.writeString(name);
        out.writeString(date);
        out.writeDouble(average);
        out.writeString(description);
    }

    private MovieItem(Parcel in) {
        this.popularity         = in.readDouble();
        this.photo              = in.readString();
        this.name               = in.readString();
        this.date               = in.readString();
        this.average            = in.readDouble();
        this.description        = in.readString();
    }

    public MovieItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }
        @Override
        public MovieItem[] newArray(int i) {
            return new MovieItem[i];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getAverage() {
        return average;
    }

    public String getDescription() {
        return description;
    }

    public Uri getFullPosterPath() {
        return Uri.parse("http://image.tmdb.org/t/p/")
                .buildUpon()
                .appendPath("w185")
                .appendEncodedPath(getPhoto())
                .build();
    }
}