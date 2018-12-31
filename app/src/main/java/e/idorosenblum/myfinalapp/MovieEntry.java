package e.idorosenblum.myfinalapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieEntry {
    public static final String CONTENT_AUTHORITY = "e.idorosenblum.myfinalapp.MyMovieProvider";

    // BASE_CONTENT_URI: content://com.sriyank.cpdemo.data.NationProvider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Path to get CLIENT APP to our table
    public static final String PATH_MOVIES = "movies";

    public static final class MovieContract implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String MOVIE_TABLE_NAME = "movies";

        public static final String MOVIE_ID = "_ID";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_RATING = "rating";
        public static final String MOVIE_YEAR_RELEASE = "pub_year";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_OVERVIEW = "overview";
    }
}

