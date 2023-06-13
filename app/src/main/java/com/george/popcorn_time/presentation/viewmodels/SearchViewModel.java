package com.george.popcorn_time.presentation.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.george.popcorn_time.data.models.MovieListResponse;
import com.george.popcorn_time.data.repositories.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {


   private final MutableLiveData<MovieListResponse> movieSearch = new MutableLiveData<>();

   private final CompositeDisposable compositeDisposable = new CompositeDisposable();



   // constructor
   private final MovieRepository repository;


    @ViewModelInject
    public SearchViewModel(MovieRepository mRepository) {
        this.repository = mRepository;
    }


    // this method returns liveData which can be observed in the view
    public LiveData<MovieListResponse> getSearchMovies(String apiKey , String query , int page){


        compositeDisposable.add(repository.getSearchMovies(apiKey,query,page)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe( movieSearchListResponse -> {

                    if(movieSearchListResponse != null){
                        movieSearch.setValue(movieSearchListResponse);
                    }

                }, error -> Log.e("SearchViewModel", error.getMessage()) ));

        return movieSearch;
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }
}
