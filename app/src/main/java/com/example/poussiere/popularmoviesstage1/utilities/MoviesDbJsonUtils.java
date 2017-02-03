package com.example.poussiere.popularmoviesstage1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**

 */

public class MoviesDbJsonUtils {



   /* // Key for the request of each poster path
    final static String TMDB_POSTERS_ARRAY="posters";
    final static String TMDB_POSTER_PATH="file_path";
*/

    final static String TMDB_MOVIES_ARRAY="results";
    final static String TMDB_POSTER_PATH="poster_path";
    final static String TMDB_ORIGINAL_TITLE="original_title";
    final static String TMDB_OVERVIEW="overview";
    final static String TMDB_VOTE_AVERAGE="vote_average";
    final static String TMDB_RELEASE_DATE="release_date";


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


    /*
    //Get a tab with all the movie Id from the JSONfile result
    public static int [] getMovieId(String jsonString) throws JSONException
    {
        int [] movieId=new int [RESULTS_NUMBER];
        JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);


        JSONObject tabRow;

        for (int i=0; i<movieId.length; i++)
        {
            tabRow=jsonMoviesArray.getJSONObject(i);
            movieId[i]=tabRow.getInt(TMDB_POSTER_PATH);

        }


        return movieId;

        //


    }

*/


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods for the detail activity


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

    public static String getSmallPosterFullUrl (String jsonString, int index) throws JSONException
    {
         JSONObject jsonObject = new JSONObject(jsonString);

        //handle possible error messages
        if (hasErrorMessage(jsonObject)) return null;
        JSONArray jsonMoviesArray = jsonObject.getJSONArray(TMDB_MOVIES_ARRAY);
        JSONObject object =jsonMoviesArray.getJSONObject(index);
        String posterFullUr=NetworkUtils.buidUrlSmallPoster(object.getString(TMDB_POSTER_PATH));
        return posterFullUr;


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
    //Method that allow to handle possible error messages in the Json Files from the movie db
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


/*
// creer une fonction pour récupérer le chemin d'un poster à partir d'un string Json

    public static String getPosterPathFromJson (String jsonString) throws JSONException
    {
        JSONObject posterPathJsonObject = new JSONObject(jsonString);


        //Handle possible error messages

        if (posterPathJsonObject.has(TMDB_ERROR))
        {
            int errorCode = posterPathJsonObject.getInt(TMDB_ERROR);

            switch (errorCode) {

                case 7:
                    return null;
                //invalid api key
                case 34:
                    return null;
                //requested resource not found
            }

        }


        //

        JSONArray postersArray = posterPathJsonObject.getJSONArray(TMDB_POSTERS_ARRAY);
        JSONObject first = postersArray.getJSONObject(0); //
        String result=first.getString(TMDB_POSTER_PATH);

        return result;


    }
*/

}
