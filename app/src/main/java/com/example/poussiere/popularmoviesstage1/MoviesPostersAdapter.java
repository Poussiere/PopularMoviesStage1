package com.example.poussiere.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by poussiere on 24/01/17.
 */

public class MoviesPostersAdapter extends RecyclerView.Adapter< MoviesPostersAdapter.MoviesPostersViewHolder> {

    private String [] posterList;



    //Constructor
    public MoviesPostersAdapter ()
    {

    }


    @Override
    public MoviesPostersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_poster, null, false);
        MoviesPostersViewHolder viewHolder = new MoviesPostersViewHolder(layoutView);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(MoviesPostersViewHolder holder, int position) {


        String posterUrl = posterList [position];
        Context context =holder.posterView.getContext();
       Picasso.with(context).load(posterUrl).into(holder.posterView);


    }


    // Return the size of the posters links array
    @Override
    public int getItemCount() {
       if (posterList==null) return 0;
        return posterList.length;
    }


    //Method to pass the array with the links of movies posters images paths to the adapter when user switch between sort by popularity and sort by top rated
    public void setMoviesPostersUrl (String[]  postersUrl)
    {
        posterList=postersUrl;
        notifyDataSetChanged();
    }





    public class MoviesPostersViewHolder extends RecyclerView.ViewHolder
    {


        public ImageView posterView;
        public ProgressBar progressBar;

        public MoviesPostersViewHolder(View itemView) {
            super(itemView);
            posterView = (ImageView)itemView.findViewById(R.id.poster_view);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress_bar_cardview);

        }
    }






}
