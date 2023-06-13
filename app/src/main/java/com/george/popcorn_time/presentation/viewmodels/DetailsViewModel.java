package com.george.popcorn_time.presentation.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.george.popcorn_time.data.models.Movie;
import com.george.popcorn_time.data.repositories.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends ViewModel {

   private final MovieRepository mRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();



    @ViewModelInject
   public DetailsViewModel(MovieRepository mRepository) {
       this.mRepository = mRepository;
   }

    public void insert(Movie movie) {

       // completable observable
        compositeDisposable.add(mRepository.insert(movie)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
        }, error -> Log.e("DetailsViewModel", error.getMessage()) ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }



}
