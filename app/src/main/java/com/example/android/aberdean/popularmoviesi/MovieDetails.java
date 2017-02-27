/*
 * Copyright (C) 2017 Antonella Bernobich Dean
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.aberdean.popularmoviesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    private ArrayList mChosenMovie;

    private ImageView mBackdrop;
    private ImageView mPosterThumb;

    private TextView mSynopsis;
    private TextView mReleaseDate;
    private TextView mOriginalTitle;
    private TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mBackdrop = (ImageView) findViewById(R.id.iv_backdrop);
        mPosterThumb = (ImageView) findViewById(R.id.iv_poster_thumb);

        mSynopsis = (TextView) findViewById(R.id.tv_synopsis);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mOriginalTitle = (TextView) findViewById(R.id.tv_title);
        mRating = (TextView) findViewById(R.id.tv_rating);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movieDetails")) {
                mChosenMovie = (ArrayList<String>)
                        intentThatStartedThisActivity.getSerializableExtra("movieDetails");

                String posterUri = mChosenMovie.get(0).toString();
                String backdropUri = mChosenMovie.get(1).toString();
                setImage(posterUri, backdropUri);

                String synopsis = mChosenMovie.get(2).toString();
                mSynopsis.setText(synopsis);

                String releaseDate = mChosenMovie.get(3).toString();
                mReleaseDate.setText("Released: " + releaseDate);

                String title = mChosenMovie.get(4).toString();
                mOriginalTitle.setText(title);

                String rating = mChosenMovie.get(5).toString();
                mRating.setText("Rating: " + rating);

            }
        }

    }

    private void setImage(String posterUri, String backdropUri) {
        String baseUri = "https://image.tmdb.org/t/p/w500";
        String posterThumb = baseUri + posterUri;
        Picasso.with(this)
                .load(posterThumb)
                .into(mPosterThumb);
        String backdrop = baseUri + backdropUri;
        Picasso.with(this)
                .load(backdrop)
                .into(mBackdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.color_scheme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FrameLayout background = (FrameLayout) findViewById(R.id.background_color);
        switch (item.getItemId()) {
            case R.id.dark_scheme:
                background.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground));
                mReleaseDate.setTextColor(ContextCompat.getColor(this, R.color.colorText));
                mRating.setTextColor(ContextCompat.getColor(this, R.color.colorText));
                mSynopsis.setTextColor(ContextCompat.getColor(this, R.color.colorText));
                return true;
            case R.id.light_scheme:
                background.setBackgroundColor(ContextCompat.getColor(this, R.color.colorText));
                mReleaseDate.setTextColor(ContextCompat.getColor(this, R.color.colorBackground));
                mRating.setTextColor(ContextCompat.getColor(this, R.color.colorBackground));
                mSynopsis.setTextColor(ContextCompat.getColor(this, R.color.colorBackground));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
