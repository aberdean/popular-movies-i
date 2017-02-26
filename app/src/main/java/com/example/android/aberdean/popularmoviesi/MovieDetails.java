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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
}
