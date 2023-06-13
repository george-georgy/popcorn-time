package com.george.popcorn_time.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.george.popcorn_time.R;
import com.george.popcorn_time.data.models.MovieListResponse;
import com.george.popcorn_time.databinding.ActivityHomeBinding;
import com.george.popcorn_time.presentation.adapters.MoviesListAdapter;
import com.george.popcorn_time.presentation.adapters.OnMovieClickListener;
import com.george.popcorn_time.presentation.adapters.TrendMoviesAdapter;
import com.george.popcorn_time.utils.Constants;
import com.george.popcorn_time.data.models.Movie;
import com.george.popcorn_time.presentation.viewmodels.HomeViewModel;


import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity implements OnMovieClickListener {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;
    private final List<Movie> trendList = new ArrayList<Movie>();
    private final List<Movie> upcomingList = new ArrayList<Movie>();
    private final List<Movie> topRatedList = new ArrayList<Movie>();


    private TrendMoviesAdapter trendListAdapter;
    private MoviesListAdapter upcomingListAdapter;
    private MoviesListAdapter topRatedListAdapter;


    private final int currentPage = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setTheme(R.style.Theme_TheMovie);
        setContentView(binding.getRoot());


        // to remove status bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);



        // Views Initialization
        trendViewPagerInitialization();
        upcomingRecyclerViewInitialization();

        getTrendMovies();
        getUpcomingMovies();
        getTopRatedMovies();

        // to search activity
        binding.searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        binding.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,WishlistActivity.class);
                startActivity(intent);
            }
        });


    }



    private void trendViewPagerInitialization(){

        // init viewpager
        binding.movieViewPager.setClipToPadding(false);
        binding.movieViewPager.setClipChildren(false);
        binding.movieViewPager.setOffscreenPageLimit(3);
        binding.movieViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        binding.movieViewPager.setPageTransformer(compositePageTransformer);
        trendListAdapter = new TrendMoviesAdapter(trendList, this,this);
        binding.movieViewPager.setAdapter(trendListAdapter);

    }
    private void upcomingRecyclerViewInitialization(){


        upcomingListAdapter = new MoviesListAdapter(upcomingList, HomeActivity.this,this);
        binding.upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.upcomingRecyclerView.setAdapter(upcomingListAdapter);

        topRatedListAdapter = new MoviesListAdapter(topRatedList, HomeActivity.this,this);
        binding.topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.topRatedRecyclerView.setAdapter(topRatedListAdapter);

    }



    private void getTrendMovies(){

        binding.trendProgressBar.setVisibility(View.VISIBLE);

        homeViewModel.getTrendMovies("all","day", Constants.API_KEY)
                .observe(this, new Observer<MovieListResponse>() {
                    @Override
                    public void onChanged(MovieListResponse movieListResponse) {

                        if(movieListResponse != null) {
                            binding.trendProgressBar.setVisibility(View.GONE);

                            // when movieSearchResponse is not null hide the progressBar

                            trendList.addAll(movieListResponse.getResult());
                            trendListAdapter.notifyDataSetChanged();
                        }

                    }
                });



    }


    private void getUpcomingMovies(){

        binding.upcomingProgressBar.setVisibility(View.VISIBLE);

        homeViewModel.getUpcomingMovies(Constants.API_KEY)
                .observe(this, new Observer<MovieListResponse>() {
                    @Override
                    public void onChanged(MovieListResponse movieListResponse) {
                        if(movieListResponse != null) {

                            binding.upcomingProgressBar.setVisibility(View.GONE);

                            // when movieSearchResponse is not null hide the progressBar

                            upcomingList.addAll(movieListResponse.getResult());
                            upcomingListAdapter.notifyDataSetChanged();
                        }

                    }
                });


    }

    private void getTopRatedMovies(){

        binding.topRatedProgressBar.setVisibility(View.VISIBLE);

        homeViewModel.getTopRatedMovies(Constants.API_KEY,"en-US",1)
                .observe(this, new Observer<MovieListResponse>() {
                    @Override
                    public void onChanged(MovieListResponse movieListResponse) {
                        binding.topRatedProgressBar.setVisibility(View.GONE);

                        // when movieSearchResponse is not null hide the progressBar

                        topRatedList.addAll(movieListResponse.getResult());
                        topRatedListAdapter.notifyDataSetChanged();

                    }
                });


    }

    // override method in OnMovieClickListener interface
    @Override
    public void onMovieClicked(Movie movie) {

        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);
    }


}
