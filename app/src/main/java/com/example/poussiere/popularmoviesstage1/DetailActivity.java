package com.example.poussiere.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.poussiere.popularmoviesstage1.R;
import com.example.poussiere.popularmoviesstage1.utilities.MoviesDbJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {


    private ImageView poster;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private RatingBar rtNoteAverage;

    private String originalTitle;
    private String posterFullUrl;
    private String releaseDate;
    private String overview;
    private float noteAverage;

    private String jsonString;
    private int index;

    //we'll set the movie title in the toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        poster = (ImageView) findViewById(R.id.iv_detailPoster);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        rtNoteAverage = (RatingBar) findViewById(R.id.rt_note_average);

        Intent i = getIntent();
        jsonString = i.getStringExtra(MainActivity.JSON_STRING);
        index = i.getIntExtra(MainActivity.INDEX, 0);

        try {
            originalTitle = MoviesDbJsonUtils.getOriginalTitleFromJson(jsonString, index);
            posterFullUrl = MoviesDbJsonUtils.getPosterFullUrl(jsonString, index);
            releaseDate = MoviesDbJsonUtils.getReleaseDate(jsonString, index);
            overview = MoviesDbJsonUtils.getOverview(jsonString, index);
            noteAverage = (float) MoviesDbJsonUtils.getNoteAverage(jsonString, index);
            }

        catch (JSONException e) {
        e.printStackTrace();}

        Picasso.with(this).load(posterFullUrl).into(poster);
        tvReleaseDate.setText(releaseDate);
        tvOverview.setText(overview);
        rtNoteAverage.setRating(noteAverage);


    }
}
