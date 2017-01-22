package com.nelson.udacity.popularmovies.ui.movieslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nelson.udacity.popularmovies.data.model.Results;
import com.nelson.udacity.popularmovies.ui.moviedetails.MovieDetailActivity;
import com.nelson.udacity.popularmovies.ui.moviedetails.MovieDetailFragment;
import com.nelson.udacity.popularmovies.R;
import com.nelson.udacity.popularmovies.utility.Constants;

import java.util.List;

/**
 * Created by nelrc on 1/2/2017.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<Results> mValues;
    private Context mContext;
    private Boolean mTwoPane;

    public RecyclerAdapter(List<Results> items, Context context, Boolean twoPane) {
        mValues = items;
        mContext = context;
        mTwoPane = twoPane;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        view.getLayoutParams().height = (int) (parent.getWidth() / Constants.number_of_columns *Constants.aspect_ratio);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String url = Constants.image_base_url + Constants.file_size+holder.mItem.getPosterPath();
        Glide.with(mContext).load(url).into(holder.imageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(MovieDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);


                    ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public Results mItem;
        public ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.imageView2);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}