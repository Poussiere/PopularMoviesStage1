package com.example.poussiere.popularmoviesstage1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MoviesDbJsonUtils {



   // Keys for the SonFile returned by TMDB

    final static String TMDB_MOVIES_ARRAY="results";
    final static String TMDB_POSTER_PATH="poster_path";
    final static String TMDB_ORIGINAL_TITLE="original_title";
    final static String TMDB_OVERVIEW="overview";
    final static String TMDB_VOTE_AVERAGE="vote_average";
    final static String TMDB_RELEASE_DATE="release_date";
    final static String TMDB_MOVIE_ID="id";


    //number of results for each page of results
    final static int RESULTS_NUMBER=20;


    //Key to handle error messages from the movie db Json
    final static String TMDB_ERROR="status_code";




    //Get a tab with all the poster paths from the JSONfile result to display posters in a GridLayout
    public static String [] getPostersFullUrl(String jsonString) throws JSONException

    {


        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);


        JSONObject tabRow;
        String [] postersUrl=new String [RESULTS_NUMBER];
        for (int i=0; i<postersUrl.length; i++)
        {
            //We get each row of the JsonArray
            tabRow=jsonMoviesArray.getJSONObject(i);

            //We convert each row of the JsonArray to String and pass it as argument to the method that construtcs the full url of posters
            postersUrl[i]=NetworkUtils.buidUrlSmallPoster(tabRow.getString(TMDB_POSTER_PATH));

        }


        return postersUrl;

        //


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods for the detail activity


    //Get the ID of the selected movie
    //The id is necessary to request video and reviews

    public static int getMovieIdFromJson (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return 0;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);

        int movieId=object.getInt(TMDB_MOVIE_ID);
        return movieId;

    }

    public static String getOriginalTitleFromJson (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        String originalTitle=object.getString(TMDB_ORIGINAL_TITLE);
        return originalTitle;

    }


    public static String getBigPosterFullUrl (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        String posterFullUr=NetworkUtils.buidUrlBigPoster(object.getString(TMDB_POSTER_PATH));
        return posterFullUr;


    }


    public static String getOverview (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        String overview=object.getString(TMDB_OVERVIEW);
        return overview;

    }

    public static double getNoteAverage (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return 0;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        double noteAverage=(double)object.getDouble(TMDB_VOTE_AVERAGE);
        return noteAverage;

    }

    public static String getReleaseDate (String jsonString, int index) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        String releaseDate=object.getString(TMDB_RELEASE_DATE);
        return releaseDate;

    }





//////////////////////////////////////////////////////////////////////////////////////////////////////
    //Method that allows to handle possible error messages in the Json Files from the movie db
    public static boolean hasErrorMessage(JSONObject jsonObject) throws JSONException
    {

        if (jsonObject.has(TMDB_ERROR))
        {
            int errorCode = jsonObject.getInt(TMDB_ERROR);

            switch (errorCode) {

                case 7:
                    return true;
                //invalid api key
                case 34:
                    return true;
                //requested resource not found

            }

        }
        return false;
    }



}
