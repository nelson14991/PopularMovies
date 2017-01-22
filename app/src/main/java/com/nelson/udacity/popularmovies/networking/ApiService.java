package com.nelson.udacity.popularmovies.networking;

import com.nelson.udacity.popularmovies.data.model.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nelrc on 12/30/2016.
 */

public interface ApiService {
    @GET("popular/")
    Observable<Movie> popularmovies(@Query("api_key") String api_key);
}
