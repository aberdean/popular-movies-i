/*
 * MIT License
 *
 * Copyright (c) 2017 Antonella Bernobich Dean
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.android.aberdean.popularmoviesi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.aberdean.popularmoviesi.utilities.MovieJsonUtils;
import com.example.android.aberdean.popularmoviesi.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;


/*TODO: Add credits page with TMDb attribution
 * https://www.themoviedb.org/about/logos-attribution) **/

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private String[][] jsonMovieData;
    private ArrayList<String> posterUris;
    private String sortBy = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        fetchPosters();

    }

    private void fetchPosters() {
        new MovieQueryTask().execute(sortBy);
    }

    public void onClick(int moviePosition) {
        Class destinationClass = MovieDetails.class;
        Intent intentToStartMovieDetails = new Intent(this, destinationClass);
        ArrayList movieDetails = getDetails(moviePosition);
        intentToStartMovieDetails.putExtra("movieDetails", movieDetails);
        startActivity(intentToStartMovieDetails);
    }

    private ArrayList getDetails(int position) {
        ArrayList<String> chosenMovie = new ArrayList(jsonMovieData.length);
        for (String[] movies : jsonMovieData) {
            chosenMovie.add(movies[position]);
        }
        return chosenMovie;
    }

    public class MovieQueryTask extends AsyncTask<String, String[], String[][]> {

        @Override
        protected String[][] doInBackground(String... params) {
            String sortOrder = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortOrder);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                jsonMovieData = MovieJsonUtils
                        .getMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return jsonMovieData;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] movieData) {
            String[] posterData = movieData[0];

            if(posterData != null) {
                posterUris = new ArrayList<>(posterData.length);
                for (String posterUri : posterData) {
                    final String basePosterUri = "https://image.tmdb.org/t/p/w500";
                    posterUris.add(basePosterUri + posterUri);
                }
                mMovieAdapter.setPosterData(posterUris);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_top_rated:
                sortBy = "vote_average.desc";
                fetchPosters();
                return true;
            case R.id.sort_by_most_popular:
                sortBy = "popularity.desc";
                fetchPosters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
