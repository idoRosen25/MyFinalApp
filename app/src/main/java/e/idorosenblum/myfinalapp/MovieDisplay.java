package e.idorosenblum.myfinalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MovieDisplay extends AppCompatActivity {

    private MovieDBHandler mMovieInstance;
    private EditText editName, editBody, editUrl;
    private Button showBtn, saveBtn, cancelBtn;
    private RatingBar rateMovie;
    private ImageView moviePoster;
    public MovieModel movieInt;

    // אקטיביטי שמציג את הסרט שנבחר מתוך הרשימה מהאינטרנט
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_edit);
        mMovieInstance = MovieDBHandler.getDataInstance();
        editName = findViewById(R.id.edit_name2);
        editBody = findViewById(R.id.edit_body2);
        editUrl = findViewById(R.id.edit_url2);
        showBtn = findViewById(R.id.show_btn2);
        showBtn.setText("Show Photo");
        saveBtn = findViewById(R.id.save_movie2);
        saveBtn.setText("Save Movie");
        cancelBtn = findViewById(R.id.cancel_movie2);
        cancelBtn.setText("Cancel");
        rateMovie = findViewById(R.id.rate_movie);
        moviePoster = findViewById(R.id.movie_image2);
        movieInt = (MovieModel) getIntent().getExtras().getSerializable(getResources().getString(R.string.movie));
        editName.setText(movieInt.getOriginal_title());
        editBody.setText(movieInt.getOverview());
        editUrl.setText("http://image.tmdb.org/t/p/w500" + movieInt.getPoster_path());
        rateMovie.setRating(movieInt.getVote_average());


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //כפתור שמציג את תמונת הסרט
                    case (R.id.show_btn2):
                        Picasso.get().load(editUrl.getText().toString()).into(moviePoster);
                        break;
                    // כפתור ששומר את הסרט במאגר
                    case (R.id.save_movie2):

                        String name_db = editName.getText().toString();
                        String url_db = movieInt.getPoster_path();
                        Float rate_db = rateMovie.getRating();
                        String year_db = movieInt.getYearOfRelease();
                        String over_db = movieInt.getOverview();

                        MovieModel movie = new MovieModel(name_db, url_db, year_db, rate_db, over_db);
                        boolean isInserted = mMovieInstance.addMovie(movie);
                        if (isInserted == true) {
                            Toast.makeText(MovieDisplay.this, "Movie Saved", Toast.LENGTH_LONG).show();
                            Intent int2 = new Intent(MovieDisplay.this, MainActivity.class);
                            startActivity(int2);
                        } else {
                            Toast.makeText(MovieDisplay.this, "Movie Not Saved", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                        break;

                    //ביטול וחזרה לרשימה מהאינטרנט
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
