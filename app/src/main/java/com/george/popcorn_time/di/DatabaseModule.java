package com.george.popcorn_time.di;

import android.app.Application;

import androidx.room.Room;
import com.george.popcorn_time.data.perisistentStorages.MovieDB;
import com.george.popcorn_time.data.perisistentStorages.MovieDao;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

/**
 * Created by Abhinav Singh on 12,June,2020
 */
@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {


    @Provides
    @Singleton
    MovieDB provideMovieDatabase(Application application){
        return Room.databaseBuilder(application,
                        MovieDB.class, "movie-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    MovieDao provideWishListDao(MovieDB MovieDB){
        return MovieDB.movieDao();
    }


}
