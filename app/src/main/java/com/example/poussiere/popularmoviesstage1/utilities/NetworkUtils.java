package com.example.poussiere.popularmoviesstage1.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 Cette classe va être utiliser pour construire les url, faire les requêtes et récupérer les résultats
 Il y aura au moins 3 méthodes : construire l'Url pour la demande de la liste de films triée par popularité, une autre par note? puis plusieurs autres pour récupérer des informations à prtir du nom d'un film.
 */

public class NetworkUtils {

    //API key is private. Please ask for your own API key to TheMovieDB
    private static final String apiKey="ac25d2d29180abf9b84a87eba3ad5316";

    private static final String BASE_URL_POPULAR_REQUEST="https://api.themoviedb.org/3/movie/popular";
    private static final String BASE_URL_TOP_RATED_REQUEST="https://api.themoviedb.org/3/movie/top_rated";

    private static final String BASE_URL_POSTER_REQUEST="http://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE="/w342/";
    private static final String BIG_POSTER_SIZE="/w500/";

   // private static final String BASE_URL_IMAGE_PATH_REQUEST="https://api.themoviedb.org/3/movie/";



    //Check the API documentation to find these params
    final static String KEY_PARAM="api_key";


    //Method that returns the url of the request to obtain the movies list sort by popularity
    public static URL buildUrlSortByPopularity()
    {
    Uri uri=Uri.parse(BASE_URL_POPULAR_REQUEST).buildUpon()
            .appendQueryParameter(KEY_PARAM, apiKey).build();

        URL url = null;
        try {
            url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



    //Method that returns the url of the request to obtain the movies list sort by top rated
    public static URL buildUrlSortByTopRated()
    {
    Uri uri=Uri.parse(BASE_URL_TOP_RATED_REQUEST).buildUpon()
            .appendQueryParameter(KEY_PARAM, apiKey).build();

        URL url = null;
        try {
            url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;


    }

    //Method that returns the url of the poster to display in GridLayout
    //We return a String because the url will be used with Picassa
    public static String buidUrlSmallPoster(String posterPath)
    {

        String stringUrl=BASE_URL_POSTER_REQUEST+SMALL_POSTER_SIZE+posterPath;

        return stringUrl;
    }

    //Method that returns the url of the poster to display in DetailActivity
    //We return a String because the url will be used with Picassa
    public static String buidUrlBigPoster(String posterPath)
    {

        String stringUrl=BASE_URL_POSTER_REQUEST+BIG_POSTER_SIZE+posterPath;
        return stringUrl;
    }

    //Method that return the result of the http request

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}


  /*  //We need the poster path in order to construct the url of each movie poster
    //Retrieve the movie id in the main from the Json array of movies
    public static String getPosterPath(int movieId)

    {
        String jsonPath=null;
        String posterPath=null;

        //  we need to know the path of the image.
        Uri pathUri=Uri.parse(BASE_URL_IMAGE_PATH_REQUEST+movieId+"images").buildUpon()
                .appendQueryParameter(KEY_PARAM, apiKey).build();
        URL requestPathUrl=null;

        try {
            requestPathUrl=new URL(pathUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // We get the json result by requesting with getResponseFromHttUrl method
        try {
            jsonPath = getResponseFromHttpUrl(requestPathUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //We get the path in String with the method of MoviesDbJsonUtils getPosterPathFromJson
        try {
           posterPath = MoviesDbJsonUtils.getPosterPathFromJson(jsonPath);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posterPath;
    }

    //This method returns the url of the poster image. The result will be passed in Picassa


  /*  public static URL buildUrlPoster(int movieId)
    {




    }

*/