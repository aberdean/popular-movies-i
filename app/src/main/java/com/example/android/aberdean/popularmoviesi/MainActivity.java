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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.aberdean.popularmoviesi.utilities.MovieJsonUtils;
import com.example.android.aberdean.popularmoviesi.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/*TODO: Add credits page with TMDb attribution
 * https://www.themoviedb.org/about/logos-attribution) **/

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private ArrayList<String> posterUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_posters);

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getBaseContext());
        mRecyclerView.setAdapter(mMovieAdapter);

        //mMoviePostersImageView = (ImageView) findViewById(R.id.iv_movie_poster);
        fetchPosters();

    }

    private void fetchPosters() {
        new MovieQueryTask().execute("popularity.desc");
    }

    public class MovieQueryTask extends AsyncTask<String, String[], String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String sortOrder = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortOrder);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                String[][] jsonMovieData = MovieJsonUtils
                        .getMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return jsonMovieData[0];

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] posterData) {
            if(posterData != null) {
                posterUris = new ArrayList<>(posterData.length);
                for (String posterUri : posterData) {
                    final String basePosterUri = "https://image.tmdb.org/t/p/w500";
                    posterUris.add(basePosterUri + posterUri);
                }
                mMovieAdapter.setPosterData(posterUris);
                //loadPosters(posterUris);
            }
        }
    }

    /*private void loadPosters(ArrayList<String> posterUris) {
        for (String posterUri : posterUris) {
            Picasso.with(this)
                    .load(posterUri)
                    .into(mMoviePostersImageView);
        }
    }*/

}
