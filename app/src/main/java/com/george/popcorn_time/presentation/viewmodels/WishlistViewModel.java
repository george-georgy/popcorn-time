package com.george.popcorn_time.presentation.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.george.popcorn_time.data.models.Movie;
import com.george.popcorn_time.data.repositories.MovieRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class WishlistViewModel extends ViewModel {
    private final MutableLiveData<List<Movie>> allWishlistMovies = new MutableLiveData<>();
    private final MovieRepository mRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();



    @ViewModelInject
    public WishlistViewModel(MovieRepository mRepository) {
        this.mRepository = mRepository;
    }

    public LiveData<List<Movie>> getAllWishlist() {

        compositeDisposable.add(mRepository.allWishlistMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( movies -> {
                    if(movies != null){
                        allWishlistMovies.setValue(movies);
                    }
                }, error -> Log.e("WishlistViewModel", error.getMessage()) ));
        return allWishlistMovies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }
}
