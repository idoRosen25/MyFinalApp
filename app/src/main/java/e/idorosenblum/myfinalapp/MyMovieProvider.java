package e.idorosenblum.myfinalapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static e.idorosenblum.myfinalapp.MovieEntry.*;
import static e.idorosenblum.myfinalapp.MovieEntry.MovieContract.MOVIE_TABLE_NAME;

public class MyMovieProvider extends ContentProvider {

    private MovieDBHandler dbInstance;
    public static final String TAG=MyMovieProvider.class.getSimpleName();
    public static Cursor providerCursor;
    private static SQLiteDatabase providerSQL;

    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#", MOVIES_ID);
    }



    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        dbInstance=MovieDBHandler.getDataInstance();
        SQLiteDatabase database = dbInstance.getReadableDatabase();
        providerCursor = database.query(MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

//        switch (uriMatcher.match(uri)) {
//
//            case MOVIES:
//                providerCursor = database.query(MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
//                break;
//
//            case MOVIES_ID:
//                selection = MovieContract._ID + " = ?";
//                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
//                providerCursor = database.query(MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
//                break;
//
//            default:
//                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
//        }

        providerCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return providerCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
