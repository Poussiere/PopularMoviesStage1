package com.example.poussiere.popularmoviesstage1;

/*


Clé de l'API (v3 auth)

ac25d2d29180abf9b84a87eba3ad5316
Jeton d'accès en lecture à l'API (v4 auth)

eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYzI1ZDJkMjkxODBhYmY5Yjg0YTg3ZWJhM2FkNTMxNiIsInN1YiI6IjU4ODY3NzUwOTI1MTQxMTI1ZTAwMzk3MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.51-VfG3E26SZgGdeyqAPhSTLQRfG_zmbq5avUYLsy3I
Exemple de requête API

https://api.themoviedb.org/3/movie/550?api_key=ac25d2d29180abf9b84a87eba3ad5316


//Principe générale
Les posters seront affichés grace à une recyclerview de card view. et gridview layout.
Dans le main, on crée un asynctask qui va envoyer la requête auprès de la moviedatabase et récupérer un tableau de string representant chaque lien vers une image.
Ce tableau de string est passé en parametre de l'adapter du recyclerview. Chaque card view chargera un lien de ce tableau grace à picassa.

Fastoche!!

Penser tout de même à demander les bonnes permissions dans le manifest


 */



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.poussiere.popularmoviesstage1.utilities.MoviesDbJsonUtils;
import com.example.poussiere.popularmoviesstage1.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesPostersAdapter.MoviesPostersAdapterOnClickHandler{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);

        //Configure toolbar and spinner that let the user choose between top rated et popular
        sortBy = getResources().getStringArray(R.array.sort_by);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sort_by, R.layout.spinner_items);
       // Spinner navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());


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
        GridLayoutManager  gridLayoutManager = new GridLayoutManager(MainActivity.this, 3); // 3 = number of items on each row
        postersRecyclerView.setLayoutManager(gridLayoutManager);

        moviesPostersAdapter=new MoviesPostersAdapter(this);
        postersRecyclerView.setAdapter(moviesPostersAdapter);


        loadMoviesData();
    }


    private void loadMoviesData()
    {
        new FetchMoviesTask().execute();

    }



    public class FetchMoviesTask extends AsyncTask<Integer, Void, String>
    {

        @Override
        protected void onPreExecute()
        {


        }


        // Au lieu de retourner un tableau de string, la méthode retournera tout simplement le fichier Json complet. Ce sera lui qui sera passé en extra également.

        @Override
        protected String doInBackground(Integer... params) {


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
        protected void onPostExecute(String jsonString)
        {

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
    }



    @Override
    public void whatMovieIndex(int index) {
        Intent i = new Intent (MainActivity.this, DetailActivity.class);
        i.putExtra(INDEX, index);
        i.putExtra(JSON_STRING,jsonStringResult );
        startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }



}
