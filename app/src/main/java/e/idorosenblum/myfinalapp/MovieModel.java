package e.idorosenblum.myfinalapp;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;

public class MovieModel implements Serializable {


    private static final String SMALL_POSTER_SIZE = "/w154";
    private static final String BIG_POSTER_SIZE = "/original";
    private boolean adult;
    private String backdrop_path;
    private int movie_id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String overview;
    private float popularity;
    private String title;
    private boolean video;
    private float vote_average;
    private int vote_count;
    private Integer isWatched;
    private Long mId;


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return movie_id;
    }

    public void setId(int id) {
        this.movie_id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setIsWatched(int watch) {
        this.isWatched = watch;
    }

    public int isWatched() {
        return isWatched;
    }

    public String getYearOfRelease() {
        if (!TextUtils.isEmpty(release_date)) {
            return release_date.substring(0, 4);
        } else {
            return "";
        }
    }

    // פונקציות לשמירה של ID הסרט המאגר
    public void setmId(Long mid) {
        this.mId = mid;
    }

    public Long getmId() {
        return mId;
    }

    //קונסטרקטור נתונים שנשמרים במאגר הSQL
    public MovieModel(String title, String poster, String year, Float rate, String view) {
        setOriginal_title(title);
        release_date = year;
        poster_path = poster;
        vote_average = rate;
        overview = view;

    }

    //קונסטרקטור לעדכון נתונים במאגר
    public MovieModel(String title, String poster, String year, Float rate, String view, Long id) {
        original_title = title;
        release_date = year;
        poster_path = poster;
        vote_average = rate;
        overview = view;
        mId = id;
    }

}