package e.idorosenblum.myfinalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MovieAdapter<E> extends ArrayAdapter<MovieModel> {

    private Context mContext;
    private ArrayList<MovieModel> mMovieList;
    private View listItem;

    //קונסטקטור של ArrayAdapter<Movies>
    public MovieAdapter(Context context_, int resource, ArrayList<MovieModel> movie_) {
        super(context_, resource, movie_);
        mContext = context_;
        mMovieList = movie_;

    }

    // פונקציה להצגת פריט ברשימת הסרטים
    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.movie_item_row, parent, false);
        }

        MovieModel currentMovie = mMovieList.get(position);

        ImageView img=listItem.findViewById(R.id.movie_img);
        Picasso.get().load("http://image.tmdb.org/t/p/w500"+currentMovie.getPoster_path()).into(img);

        TextView name = listItem.findViewById(R.id.movie_title);
        name.setText(currentMovie.getOriginal_title());

        TextView rating = listItem.findViewById(R.id.movie_rating);
        rating.setText(String.valueOf(currentMovie.getVote_average()));

        TextView pubDate = listItem.findViewById(R.id.movie_pub_date);
        pubDate.setText(currentMovie.getYearOfRelease());

        return listItem;
    }
}
