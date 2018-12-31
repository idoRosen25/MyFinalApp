package e.idorosenblum.myfinalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MovieEdit extends AppCompatActivity {
    MovieDBHandler mMovieInstance;
    private EditText editName, editBody, editUrl;
    private Button showBtn, saveBtn, cancelBtn;
    private RatingBar rateMovie;
    private ImageView moviePoster;
    public MovieModel movieInt;
    private View.OnClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_edit);
        mMovieInstance = MovieDBHandler.getDataInstance();

        movieInt = (MovieModel) getIntent().getExtras().getSerializable(getResources().getString(R.string.movie));
        editName = findViewById(R.id.edit_name2);
        editBody = findViewById(R.id.edit_body2);
        editUrl = findViewById(R.id.edit_url2);
        rateMovie = findViewById(R.id.rate_movie);
        rateMovie.setRating(movieInt.getVote_average());
        moviePoster = findViewById(R.id.movie_image2);
        editBody.setText(movieInt.getOverview());
        editName.setText(movieInt.getOriginal_title());
        editUrl.setText(movieInt.getPoster_path());
        showBtn = findViewById(R.id.show_btn2);
        saveBtn = findViewById(R.id.save_movie2);
        cancelBtn = findViewById(R.id.cancel_movie2);
        cancelBtn.setText("Cancel");

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //כפתור לתצוגת פוסטר של הסרט
                    case (R.id.show_btn2):
                        Picasso.get().load("http://image.tmdb.org/t/p/w500"+movieInt.getPoster_path()).into(moviePoster);
                        break;
                    // שמירת הסרט המעודכן במאגר
                    case (R.id.save_movie2):
                        String name_db = editName.getText().toString();
                        String year_db = movieInt.getYearOfRelease();
                        String url_db = editUrl.getText().toString();
                        Float rate_db = rateMovie.getRating();
                        Long id_db = movieInt.getmId();
                        String overview_db = editBody.getText().toString();

                        MovieModel movie = new MovieModel(name_db, url_db, year_db, rate_db, overview_db, id_db);
                        boolean isInserted = mMovieInstance.updateMovie(movie);
                        if (isInserted == true) {
                            Toast.makeText(MovieEdit.this, "Movie Saved", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(MovieEdit.this, "Movie Not Saved", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                        break;
                    //ביטול וחזרה למסך הראשי
                    case (R.id.cancel_movie2):
                        onBackPressed();
                        break;
                }
            }
        };
        showBtn.setOnClickListener(listener);
        saveBtn.setOnClickListener(listener);
        cancelBtn.setOnClickListener(listener);
    }
}