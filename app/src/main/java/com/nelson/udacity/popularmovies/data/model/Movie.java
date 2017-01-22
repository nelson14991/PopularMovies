package com.nelson.udacity.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nelrc on 12/30/2016.
 */

public final class Movie implements Parcelable {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<Results> results = null;
    @SerializedName("total_results")
    @Expose
    private long totalResults;
    @SerializedName("total_pages")
    @Expose
    private long totalPages;

    protected Movie(Parcel in) {
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public List<Results> getResults() {
        return results;
    }


}
