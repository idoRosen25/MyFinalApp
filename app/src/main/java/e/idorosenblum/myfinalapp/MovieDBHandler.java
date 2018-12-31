package e.idorosenblum.myfinalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import e.idorosenblum.myfinalapp.MovieEntry.MovieContract;

import java.util.ArrayList;

import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_ID;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_OVERVIEW;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_POSTER;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_RATING;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_TABLE_NAME;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_TITLE;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_YEAR_RELEASE;

public class MovieDBHandler extends SQLiteOpenHelper {

    public static MovieDBHandler dataInstance;
    private static final String TAG = "MovieDBHandler";
    private static final String TABLE_NAME = "my_movies";


    private MovieDBHandler(Context context) {
        super(context, MOVIE_TABLE_NAME, null, 1);
    }

    //קונסטרקטור סינגלטון על מנת לאתחל DataBase רק פעם אחת
    public static MovieDBHandler getDataInstance() {
        if (dataInstance == null) {
            dataInstance = new MovieDBHandler(MainActivity.mainContext);
        }
        return dataInstance;
    }

    // פונקציה שכותבת את הSQL בפעם הראשונה
    @Nullable
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MOVIE_TABLE_NAME + " ("
                + MOVIE_ID + " INTEGER PRIMARY KEY, "
                + MOVIE_TITLE + "  TEXT, "
                + MOVIE_RATING + " REAL, "
                + MOVIE_YEAR_RELEASE + " INTEGER, "
                + MOVIE_POSTER + " TEXT, "
                + MOVIE_OVERVIEW + " TEXT " + ")";
        try {
            sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE);
        } catch (SQLiteException e) {
            Log.e("", "");
        }
    }

    // פונקציה שמוסיפה סרט למאגר נתונים
    public boolean addMovie(MovieModel movie_) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_TITLE, movie_.getOriginal_title());
        contentValues.put(MOVIE_RATING, movie_.getVote_average());
        contentValues.put(MOVIE_YEAR_RELEASE, movie_.getYearOfRelease());
        contentValues.put(MOVIE_POSTER, movie_.getPoster_path());
        contentValues.put(MOVIE_OVERVIEW, movie_.getOverview());
        try {
            Long resultID = db.insertOrThrow(MOVIE_TABLE_NAME, "", contentValues);
            movie_.setmId(resultID);
            Log.d(TAG, "movie insert with id: " + resultID + ", movie title: " + movie_.getOriginal_title());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.close();
        }

        return true;
    }

    //פונקציה שמעדכנת שינוי נתונים במאגר הנתונים
    public boolean updateMovie(MovieModel movie_) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_TITLE, movie_.getOriginal_title());
        contentValues.put(MOVIE_RATING, movie_.getVote_average());
        contentValues.put(MOVIE_YEAR_RELEASE, movie_.getYearOfRelease());
        contentValues.put(MOVIE_POSTER, movie_.getPoster_path());
        contentValues.put(MOVIE_OVERVIEW, movie_.getOverview());
        try {
            db.update(MOVIE_TABLE_NAME, contentValues, MOVIE_ID + "=?", new String[]{String.valueOf(movie_.getmId())});
            Log.d(TAG, "movie insert with id: " + movie_.getmId() + ", movie title: " + movie_.getOriginal_title());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.close();
        }
        return true;
    }


    private Cursor getAllMovies() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MOVIE_TABLE_NAME, null, null, null, null, null, null, null);
        return cursor;
    }

    // פונקציה שמחזירה את כל הסרטים שנשמרו במאגר
    public ArrayList<MovieModel> getDbMovies() {

        ArrayList<MovieModel> movieList = new ArrayList<>();

        Cursor cursor = getAllMovies();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(MOVIE_ID));

            String title = cursor.getString(cursor.getColumnIndex(MOVIE_TITLE));
            Float rating = cursor.getFloat(cursor.getColumnIndex(MOVIE_RATING));
            String release_date = cursor.getString(cursor.getColumnIndex(MOVIE_YEAR_RELEASE));
            String url = cursor.getString(cursor.getColumnIndex(MOVIE_POSTER));
            String overView = cursor.getString(cursor.getColumnIndex(MOVIE_OVERVIEW));


            MovieModel movie = new MovieModel(title, url, release_date, rating, overView, Long.valueOf(id));

            movieList.add(movie);

        }
        return movieList;
    }

    // פונקציה שמוחקת סרט מסויים מהמאגר
    public void deleteMovie(MovieModel movie_) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete(MOVIE_TABLE_NAME, MOVIE_ID + "=?", new String[]{String.valueOf(movie_.getmId())});
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.close();
        }
    }

    // פונקציה שמוחקת את כל הסרטים מהרשימה
    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + MOVIE_TABLE_NAME);

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.close();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}