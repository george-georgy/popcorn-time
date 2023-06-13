package com.george.popcorn_time.data.perisistentStorages;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.george.popcorn_time.data.models.Movie;


@Database(entities = {Movie.class}, version = 3)
public abstract class MovieDB extends RoomDatabase {


   public abstract MovieDao movieDao();


}
