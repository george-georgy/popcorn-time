package com.george.popcorn_time.presentation.adapters;

// HomeActivity implements this interface
/* we have this interface for sending click info to the activity but do actually detect the click well, we
   still need to use an onClickListener in the viewHolder

 */

import com.george.popcorn_time.data.models.Movie;

public interface OnMovieClickListener {

    void onMovieClicked(Movie movie);
}
