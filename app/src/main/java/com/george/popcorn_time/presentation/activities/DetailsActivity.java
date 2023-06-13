package com.george.popcorn_time.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.george.popcorn_time.databinding.ActivityDetailsBinding;
import com.george.popcorn_time.utils.Constants;
import com.george.popcorn_time.data.models.Movie;
import com.george.popcorn_time.presentation.viewmodels.DetailsViewModel;


import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    private Movie movie;

    private DetailsViewModel detailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // to remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //
        movie = (Movie) getIntent().getSerializableExtra("movie");


        //
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);


        // getting info of
        observeAnyChangesMovieDetails();

        binding.imageWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detailsViewModel.insert(movie);
                Toast.makeText(DetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();

            }
        });

        // back
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }




    private void observeAnyChangesMovieDetails(){

        binding.detailsProgressBar.setVisibility(View.GONE);

        Glide.with(getApplicationContext())
                .load(Constants.IMAGE_URL + movie.getPosterPath()).into(binding.detailMovieImg);
        binding.detailMovieImg.setVisibility(View.VISIBLE);

        binding.detailsMovieTitle.setText(movie.getTitle());
        binding.detailsMovieReleaseDate.setText(movie.getReleaseDate());
        binding.detailMovieDesc.setText(movie.getMovieOverview());
        binding.detailRatingBar.setRating(movie.getVoteAverage()/2);
        binding.detailsLang.setText("("+movie.getOriginalLanguage()+")");


    }




}