package com.example.poussiere.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.poussiere.popularmoviesstage1.R;
import com.example.poussiere.popularmoviesstage1.utilities.MoviesDbJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {


    private ImageView poster;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private RatingBar rtNoteAverage;

    private String originalTitle;
    private String posterFullUrl;
    private String releaseDate;
    private String overview;
    private float noteAverage;

    private String jsonString;
    private Toolbar toolbar;
    private int index;

    //we'll set the movie title in the toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.detail_activity_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);


        poster = (ImageView) findViewById(R.id.iv_detailPoster);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        rtNoteAverage = (RatingBar) findViewById(R.id.rt_note_average);


    }
@Override
protected void onResume()
{
    super.onResume();

    Intent i = getIntent();
    jsonString = i.getStringExtra(MainActivity.JSON_STRING);
    index = i.getIntExtra(MainActivity.INDEX, 0);

    try {
        originalTitle = MoviesDbJsonUtils.getOriginalTitleFromJson(jsonString, index);
        posterFullUrl = MoviesDbJsonUtils.getBigPosterFullUrl(jsonString, index);
        releaseDate = MoviesDbJsonUtils.getReleaseDate(jsonString, index);
        overview = MoviesDbJsonUtils.getOverview(jsonString, index);
        noteAverage = (float) MoviesDbJsonUtils.getNoteAverage(jsonString, index);
    }


    catch (JSONException e) {
        e.printStackTrace();}

    Picasso.with(this).load(posterFullUrl).into(poster);
    tvTitle.setText(originalTitle);
    tvReleaseDate.setText(releaseDate);
    tvOverview.setText(overview);
    rtNoteAverage.setRating((float) (noteAverage/2.0));


}

    //Toolbar back button listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
