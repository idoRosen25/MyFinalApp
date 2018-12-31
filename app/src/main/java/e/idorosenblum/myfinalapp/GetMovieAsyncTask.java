package e.idorosenblum.myfinalapp;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetMovieAsyncTask extends AsyncTask<String, Integer, ArrayList<MovieModel>> {

    private MovieSearch main;
    private ArrayList<MovieModel> mMovieList;

    public GetMovieAsyncTask(MovieSearch searchActivity) {
        main = searchActivity;
    }

    // פונקציה שמקבלת רשימת סרטים מהאינטרנט
    @Override
    protected ArrayList<MovieModel> doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();
        String urlQuery = urls[0];
        Request request = new Request.Builder()
                .url(urlQuery)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) try {
            throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return getMoviesListFromJson(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //פונקציה שמקבלת את רשימת הסרטים ומציגה אותם ברשימה
    @Override
    protected void onPostExecute(ArrayList<MovieModel> movieModels) {
        main.showMovies(movieModels);
        main.stopProgressDialog();

    }

    public ArrayList<MovieModel> getMoviesListFromJson(String jsonResponse) {
        List<MovieModel> stubMovieData;
        Gson gson = new GsonBuilder().create();
        MovieResponse response = gson.fromJson(jsonResponse, MovieResponse.class);
        stubMovieData = response.results;
        ArrayList<MovieModel> arrList = new ArrayList<>();
        arrList.addAll(stubMovieData);
        return arrList;
    }


    public class MovieResponse {

        private List<MovieModel> results;

        // public constructor is necessary for collections
        public MovieResponse() {
            results = new ArrayList<MovieModel>();
        }

    }

}
