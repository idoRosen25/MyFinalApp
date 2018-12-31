package e.idorosenblum.myfinalapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<MovieModel> mMovieList;
    private MovieAdapter<MovieModel> adapter;
    public static MainActivity mainContext;
    private MovieDBHandler myMovieDB;
    private ListView savedList;
    private TextView noMovie;

    //DOING SOMTHING  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onStart();
        noMovie = findViewById(R.id.no_text);
        if (mMovieList.size() <= 0) {
            savedList.setVisibility(View.INVISIBLE);
            noMovie.setVisibility(View.VISIBLE);
            noMovie.setText("No Saved Movies. \nPlease Add Movies.");
        } else {
            noMovie.setVisibility(View.INVISIBLE);
        }
    }

    // אקטיביטי שמציג את רשימת הסרטים שנשמרו בDataBase
    @Override
    protected void onStart() {

        mainContext = this;
        myMovieDB = MovieDBHandler.getDataInstance();
        mMovieList = myMovieDB.getDbMovies();
        savedList = findViewById(R.id.save_list);
        adapter = new MovieAdapter<>(this, android.R.layout.simple_list_item_1, mMovieList);
        savedList.setVisibility(View.VISIBLE);
        savedList.setAdapter(adapter);
        savedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieModel clickedMovie = mMovieList.get(position);
                Intent intent = new Intent(MainActivity.this, MovieEdit.class);
                intent.putExtra(getResources().getString(R.string.movie), clickedMovie);
                MainActivity.this.startActivity(intent);
            }
        });
        registerForContextMenu(savedList);
        super.onStart();


    }

    // מטודות לפתיחת תפריט אפשרויות (3 נקודות בפינת המסך)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.manual:
                Intent intent = new Intent(MainActivity.this, MovieAdd.class);
                intent.putExtra(getResources().getString(R.string.sql_movie), "");
                startActivity(intent);
                break;
            case R.id.internet:
                Intent intent2 = new Intent(MainActivity.this, MovieSearch.class);
                startActivity(intent2);
                break;
            case R.id.delete:
                myMovieDB.clearAllData();
                adapter.clear();
                adapter.notifyDataSetChanged();
                savedList.setVisibility(View.INVISIBLE);
                noMovie.setVisibility(View.VISIBLE);
                noMovie.setText("No Saved Movies. \nPlease Add Movies.");
                break;
        }
        return true;
    }

    //מטודות לפתיחת תפריט בלחיצה ארוכה על הסרט
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.delete:
                myMovieDB.deleteMovie(adapter.getItem(position));
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                if (position <= 0 && adapter.getCount() == 0) {
                    savedList.setVisibility(View.INVISIBLE);
                    noMovie.setVisibility(View.VISIBLE);
                    noMovie.setText("No Saved Movies. \nPlease Add Movies.");
                }
                break;
            case R.id.edit:
                Intent movieInt = new Intent(MainActivity.this, MovieEdit.class);
                movieInt.putExtra(getResources().getString(R.string.movie), adapter.getItem(position));
                startActivity(movieInt);
                break;
        }
        return true;
    }
}
