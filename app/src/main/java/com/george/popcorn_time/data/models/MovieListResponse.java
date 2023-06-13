package com.george.popcorn_time.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// This class used for Now Playing and Up coming movies.

public class MovieListResponse {


    @SerializedName("results")
    private List<Movie> result;
    @SerializedName("page")
    private int respondPageNumber;
    @SerializedName("total_pages")
    private int respondTotalPageNumber;

    public List<Movie> getResult() {
        return result;
    }

    public int getRespondPageNumber() {
        return respondPageNumber;
    }

    public int getRespondTotalPageNumber() {
        return respondTotalPageNumber;
    }

    @Override
    public String toString() {
        return "FilmsRespond{" +
                "result=" + result +
                ", respondPageNumber=" + respondPageNumber +
                ", respondTotalPageNumber=" + respondTotalPageNumber +
                '}';
    }
}