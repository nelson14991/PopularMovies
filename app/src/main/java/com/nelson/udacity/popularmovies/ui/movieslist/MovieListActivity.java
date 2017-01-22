package com.nelson.udacity.popularmovies.ui.movieslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nelson.udacity.popularmovies.data.model.Movie;
import com.nelson.udacity.popularmovies.data.model.Results;
import com.nelson.udacity.popularmovies.networking.ApiService;
import com.nelson.udacity.popularmovies.R;
import com.nelson.udacity.popularmovies.utility.Constants;
import com.nelson.udacity.popularmovies.utility.Utility;
import com.nelson.udacity.popularmovies.ui.moviedetails.MovieDetailActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private final static String MOST_POPULAR = "most_popular";
    private final static String TOP_RATED = "top_rated";
    private final static String FAVORITES = "favorites";
    private String mSortBy ="";
    RecyclerAdapter adapter;
    private boolean mTwoPane;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        Utility utility = new Utility(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        if (utility.isConnectedToInternet()) {
            ApiService apiService = retrofit.create(ApiService.class);
            Observable<Movie> popularmovies = apiService.popularmovies(Constants.api_key);
            popularmovies.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new DisposableObserver<Movie>() {

                        @Override
                        public void onNext(Movie value) {
                            List<Results> results = value.getResults();
                            adapter = new RecyclerAdapter(results, getApplicationContext(), mTwoPane);
                            setupRecyclerView((RecyclerView) recyclerView);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_list_menu, menu);

        /*switch (mSortBy) {
            case MOST_POPULAR:
                menu.findItem(R.id.sort_by_most_popular).setChecked(true);
                break;
            case TOP_RATED:
                menu.findItem(R.id.sort_by_top_rated).setChecked(true);
                break;
            case FAVORITES:
                menu.findItem(R.id.sort_by_favorites).setChecked(true);
                break;
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_top_rated:

              /*  mSortBy = FetchMoviesTask.TOP_RATED;
                fetchMovies(mSortBy);
                item.setChecked(true);*/
                break;
            case R.id.sort_by_most_popular:

              /*  mSortBy = FetchMoviesTask.MOST_POPULAR;
                fetchMovies(mSortBy);
                item.setChecked(true);*/
                break;
            case R.id.sort_by_favorites:
               /* mSortBy = FetchMoviesTask.FAVORITES;
                item.setChecked(true);
                fetchMovies(mSortBy);*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
    }
}
