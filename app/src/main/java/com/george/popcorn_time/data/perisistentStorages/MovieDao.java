package com.george.popcorn_time.data.perisistentStorages;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.george.popcorn_time.data.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

 @Insert
 Completable insert(Movie movie);

 @Query("SELECT * From Movie")
 Single<List<Movie>> allWishlistMovies();
}
