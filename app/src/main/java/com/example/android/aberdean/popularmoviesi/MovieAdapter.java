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
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter
        extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList mPosterData;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;

    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mPosterImageView;

        public MovieAdapterViewHolder (View view) {
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForPosters = R.layout.poster_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPosters, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String posterUrl = mPosterData.get(position).toString();
        Picasso.with(context)
                .load(posterUrl)
                .into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        if (mPosterData == null) return 0;
        return mPosterData.size();
    }

    public void setPosterData (ArrayList posterData) {
        mPosterData = posterData;
        notifyDataSetChanged();
    }
}
