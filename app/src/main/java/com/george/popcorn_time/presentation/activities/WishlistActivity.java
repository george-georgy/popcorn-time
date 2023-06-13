package com.george.popcorn_time.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.george.popcorn_time.data.models.Movie;
import com.george.popcorn_time.databinding.ActivityWishlistBinding;
import com.george.popcorn_time.presentation.adapters.OnMovieClickListener;
import com.george.popcorn_time.presentation.adapters.WishListMoviesAdapter;
import com.george.popcorn_time.presentation.viewmodels.WishlistViewModel;


import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WishlistActivity extends AppCompatActivity implements OnMovieClickListener {

    // binding
    private ActivityWishlistBinding binding;

    // view Model
    private WishlistViewModel wishlistViewModel;

    // Adapter
    private WishListMoviesAdapter adapter;


    private List<Movie> wishlist = new ArrayList<Movie>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);

        viewInit();

        //View Model
        wishlistViewModel.getAllWishlist().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                wishlist.addAll(movies);
                adapter.notifyDataSetChanged();

            }
        });


    }

    public void viewInit(){

        //recycler view
        binding.rvWishlist.setLayoutManager(new LinearLayoutManager(this));
        binding.rvWishlist.setHasFixedSize(true);

        //connect Recyclerview With adapter
        adapter = new WishListMoviesAdapter(wishlist,this, this);
        binding.rvWishlist.setAdapter(adapter);

    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);
    }
}