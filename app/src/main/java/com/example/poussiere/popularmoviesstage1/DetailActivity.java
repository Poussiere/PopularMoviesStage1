package com.example.poussiere.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.poussiere.popularmoviesstage1.utilities.MoviesDbJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {


    private ImageView poster;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvRate;
    private RatingBar rtRate;
    private int movieId;
    private String originalTitle;
    private String posterFullUrl;
    private String releaseDate;
    private String overview;
    private float noteAverage;
    private String jsonString;
    private Toolbar toolbar;
    private int index;



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
        tvRate=(TextView)findViewById((R.id.tv_note));
        rtRate = (RatingBar) findViewById(R.id.rt_note_average);


    }
@Override
protected void onResume()
{
    super.onResume();

    Intent i = getIntent();


        movieId=i.getIntExtra(MainActivity.MOVIE_ID, 0);
        originalTitle = i.getStringExtra(MainActivity.ORIGINAL_TITLE);
        posterFullUrl = i.getStringExtra(MainActivity.POSTER_FULL_URL);
        releaseDate = i.getStringExtra(MainActivity.RELEASE_DATE);
        overview = i.getStringExtra(MainActivity.OVERVIEW);
        noteAverage = i.getFloatExtra(MainActivity.NOTE_AVERAGE, 0);


    Picasso.with(this).load(posterFullUrl).into(poster);
    tvTitle.setText(originalTitle);
    tvReleaseDate.setText(releaseDate);
    tvOverview.setText(overview);
    tvRate.setText(noteAverage+"/5");
    rtRate.setRating(noteAverage);


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
