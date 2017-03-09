package com.example.poussiere.popularmoviesstage1;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.example.poussiere.popularmoviesstage1.utilities.MoviesDbJsonUtils;
import com.example.poussiere.popularmoviesstage1.utilities.NetworkUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesPostersAdapter.MoviesPostersAdapterOnClickHandler, LoaderManager.LoaderCallbacks<String>{

    private static final int ASYNC_LOADER_ID = 42;

    private final static int SORT_BY_POPULARITY = 0;
    private final static int SORT_BY_TOP_RATED = 1;

    public final static String INDEX = "index";
    public final static String JSON_STRING="json";

    private int sortChoice = SORT_BY_POPULARITY ;// By default movie posters are sort by popularity

    private RecyclerView postersRecyclerView;
    private MoviesPostersAdapter moviesPostersAdapter;

    private Toolbar toolbar = null;

    //array for the spinner (choice between popular vs top rated
    private String [] sortBy = null;
    private Spinner spinner;
    private String jsonStringResult;

    // Key for intent extras that will be passed to detail activity
    public static final String MOVIE_ID="movie_id";
    public static final String ORIGINAL_TITLE="original_title";
    public static final String POSTER_FULL_URL="poster_full_url";
    public static final String RELEASE_DATE="release_date";
    public static final String OVERVIEW="overview";
    public static final String NOTE_AVERAGE="note_average";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);

        //Configure toolbar and spinner that let the user choose between top rated et popular
        sortBy = getResources().getStringArray(R.array.sort_by);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Make title white
       toolbar.setTitleTextColor(Color.WHITE);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sort_by, R.layout.spinner_items);



        spinner.setAdapter(spinnerAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortChoice=position;
                loadMoviesData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Configure recycler view in GrilLayout
        postersRecyclerView=(RecyclerView)findViewById(R.id.posters_recyclerview);


        postersRecyclerView.setHasFixedSize(true);
        GridLayoutManager  gridLayoutManager = new GridLayoutManager(MainActivity.this, 2); // 3 = number of items on each row
        postersRecyclerView.setLayoutManager(gridLayoutManager);

        moviesPostersAdapter=new MoviesPostersAdapter(this);
        postersRecyclerView.setAdapter(moviesPostersAdapter);


        getSupportLoaderManager().initLoader(ASYNC_LOADER_ID, null, this);
    }


    private void loadMoviesData()
    {


        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieQuerryLoader = loaderManager.getLoader(ASYNC_LOADER_ID);
        if (movieQuerryLoader == null) {
            loaderManager.initLoader(ASYNC_LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(ASYNC_LOADER_ID, null, this);
        }

    }



    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            // COMPLETED (1) Create a String member variable called mGithubJson that will store the raw JSON
            /* This String will contain the raw JSON from the results of our Github search */
            String jsonResult;

            @Override
            protected void onStartLoading() {

                /* If no arguments were passed, we don't have a query to perform. Simply return.
                if (args == null) {
                    return;
                }
                */

                if (jsonResult != null) {
                    deliverResult(jsonResult);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                URL movieListRequest = null;

                String jsonMovieResponse=null;


                if (sortChoice == SORT_BY_POPULARITY) {
                    movieListRequest = NetworkUtils.buildUrlSortByPopularity();}
                else
                {movieListRequest = NetworkUtils.buildUrlSortByTopRated();}

                try {
                    jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieListRequest);





                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }


                return jsonMovieResponse;
            }


            @Override
            public void deliverResult(String jsonMovieResponse) {
                jsonResult = jsonMovieResponse;
                super.deliverResult(jsonResult);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String jsonString) {

        jsonStringResult=jsonString;

        String[] postersFullUrl=null;

        if (jsonString!=null)
        {

            try {
                postersFullUrl = MoviesDbJsonUtils.getPostersFullUrl(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            moviesPostersAdapter.setMoviesPostersUrl(postersFullUrl);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }


    //This method is trigered when an item is clicked
    @Override
    public void whatMovieIndex(int index) {
        Intent i = new Intent (MainActivity.this, DetailActivity.class);
        try {
            int movieId=MoviesDbJsonUtils.getMovieIdFromJson(jsonStringResult, index);
           String originalTitle = MoviesDbJsonUtils.getOriginalTitleFromJson(jsonStringResult, index);
           String posterFullUrl = MoviesDbJsonUtils.getBigPosterFullUrl(jsonStringResult, index);
           String releaseDate = MoviesDbJsonUtils.getReleaseDate(jsonStringResult, index);
            String  overview = MoviesDbJsonUtils.getOverview(jsonStringResult, index);
           float noteAverage = (float) MoviesDbJsonUtils.getNoteAverage(jsonStringResult, index);
            noteAverage = noteAverage/2; //we want only 5 stars max but by default the rate is /10
            i.putExtra(MOVIE_ID, movieId);
            i.putExtra(ORIGINAL_TITLE, originalTitle);
            i.putExtra(POSTER_FULL_URL, posterFullUrl);
            i.putExtra(RELEASE_DATE, releaseDate);
            i.putExtra(OVERVIEW, overview);
            i.putExtra(NOTE_AVERAGE, noteAverage);
        }

        catch (JSONException e) {
            e.printStackTrace();}

        startActivity(i);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }



}
