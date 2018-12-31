package e.idorosenblum.myfinalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieSearch extends AppCompatActivity {

    private ArrayList<MovieModel> mSearchList;
    private MovieAdapter<MovieModel> adapter;
    private SearchView search;
    private MovieDBHandler mdbHandler;
    private static ProgressDialog mProgressDialog;
    private GetMovieAsyncTask getMoviesAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_search);

        ListView listView = findViewById(R.id.search_list);

        search = findViewById(R.id.movie_search);
        if (mSearchList == null) {

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getMoviesAsyncTask = new GetMovieAsyncTask(MovieSearch.this);
                    String urlQuery = "http://api.themoviedb.org/3/search/movie?&query=" + query + "&api_key=efb4a63e1afce166e54678a2c407af4b";
                    getMoviesAsyncTask.execute(urlQuery);
                    mProgressDialog = ProgressDialog.show(MovieSearch.this, "", "Loading Movies. Please Wait...", true);
                    return true;

                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
            // TODO: feel the list
            //get movies
            //String urlQuery = "http://api.themoviedb.org/3/search/movie?&query=matrix&api_key=efb4a63e1afce166e54678a2c407af4b" + String.valueOf(3);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MovieModel movie = mSearchList.get(position);
                    Intent intent = new Intent(MovieSearch.this, MovieDisplay.class);
                    intent.putExtra(getResources().getString(R.string.movie), movie);
                    startActivity(intent);
                }
            });

            registerForContextMenu(listView);

        }
    }


    public void showMovies(ArrayList<MovieModel> movieModels) {


        ListView list_view = findViewById(R.id.search_list);
        mSearchList = movieModels;
        adapter = new MovieAdapter<>(this, android.R.layout.simple_list_item_1, mSearchList);
        list_view.setAdapter(adapter);

    }

    public static void stopProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
