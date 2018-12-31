package e.idorosenblum.myfinalapp;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class MovieAdd extends AppCompatActivity {

    MovieDBHandler mMovieInstance;
    private EditText editName, editBody, editUrl;
    private Button showBtn, saveBtn, cancelBtn;
    private RatingBar rateMovie;
    private ImageView moviePoster;
    public MovieModel movieInt;
    private View.OnClickListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_edit);

        editName = findViewById(R.id.edit_name2);
        editName.setText("");
        editBody = findViewById(R.id.edit_body2);
        editBody.setText("");
        editUrl = findViewById(R.id.edit_url2);
        editUrl.setText("");
        rateMovie = findViewById(R.id.rate_movie);
        rateMovie.setRating(0);
        moviePoster = findViewById(R.id.movie_image2);
        showBtn = findViewById(R.id.show_btn2);
        showBtn.setText("Take Photo");
        saveBtn = findViewById(R.id.save_movie2);
        saveBtn.setText("Save Movie");
        cancelBtn = findViewById(R.id.cancel_movie2);
        cancelBtn.setText("Cancel");

        mMovieInstance = MovieDBHandler.getDataInstance();

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.show_btn2):
                        Toast.makeText(MovieAdd.this, "Please Save Movie", Toast.LENGTH_SHORT).show();
                        break;
                    // שמירת הסרט במאגר
                    case (R.id.save_movie2):
                        String name_db = editName.getText().toString();
                        String year_db = "";
                        Float rate_db = rateMovie.getRating();
                        Long id_db = null;
                        String overview_db = editBody.getText().toString();
                        String url_db = editUrl.getText().toString();
                        MovieModel movie = new MovieModel(name_db, url_db, year_db, rate_db, overview_db, id_db);
                        boolean isInserted = mMovieInstance.addMovie(movie);
                        if (isInserted == true) {
                            Toast.makeText(MovieAdd.this, "Movie Saved", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(MovieAdd.this, "Movie Not Saved", Toast.LENGTH_LONG).show();
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