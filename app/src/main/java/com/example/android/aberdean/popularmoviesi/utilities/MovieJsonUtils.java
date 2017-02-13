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

package com.example.android.aberdean.popularmoviesi.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle TMDb JSON data.
 */
public final class MovieJsonUtils {

    /**
     * Parse JSON from a TMDb response and return an array of Strings
     * describing a movie.
     *
     * @param movieJsonString JSON response from server
     *
     * @return Array of Strings describing a movie
     *
     * @throws JSONException If JSON data cannot be parsed
     */
    public static String[][] getMovieStringsFromJson(Context context, String movieJsonString)
            throws JSONException {

        /* Movie information. All info for a movie are children of the "results" array */
        final String MJ_RESULTS = "results";

        /* Path to the poster image for the movie */
        final String MJ_POSTER = "poster_path";
        /* Movie description */
        final String MJ_OVERVIEW = "overview";
        final String MJ_RELEASE_DATE = "release_date";
        final String MJ_ORIGINAL_TITLE = "original_title";
        final String MJ_VOTE_AVERAGE = "vote_average";

        /* HTTP status codes */
        final String MJ_STATUS_CODE = "status_code";

        /* String arrays holding each movie's data */
        String[] parsedPosterUri;
        String[] parsedDescription;
        String[] parsedReleaseDate;
        String[] parsedTitle;
        String[] parsedRating;

        JSONObject movieJson = new JSONObject(movieJsonString);

        /* Handle connectivity errors */
        if (movieJson.has(MJ_STATUS_CODE)) {
            int errorCode = movieJson.getInt(MJ_STATUS_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MJ_RESULTS);

        parsedPosterUri = new String[movieArray.length()];
        parsedDescription = new String[movieArray.length()];
        parsedReleaseDate = new String[movieArray.length()];
        parsedTitle = new String[movieArray.length()];
        parsedRating = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            /* Values to be collected */
            String posterPath;
            String description;
            String releaseDate;
            String title;
            String rating;

            /* Get the JSON object representing the movie */
            JSONObject movieData = movieArray.getJSONObject(i);

            posterPath = movieData.getString(MJ_POSTER);
            description = movieData.getString(MJ_OVERVIEW);
            releaseDate = movieData.getString(MJ_RELEASE_DATE);
            title = movieData.getString(MJ_ORIGINAL_TITLE);
            rating = movieData.getString(MJ_VOTE_AVERAGE);

            parsedPosterUri[i] = posterPath;
            parsedDescription[i] = description;
            parsedReleaseDate[i] = releaseDate;
            parsedTitle[i] = title;
            parsedRating[i] = rating;

        }

        String[][] parsedMovieData = {parsedPosterUri, parsedDescription, parsedReleaseDate,
                parsedTitle, parsedRating};

        return parsedMovieData;
    }
}
