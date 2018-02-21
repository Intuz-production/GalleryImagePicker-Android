package com.intuz.customgallerypicker.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.intuz.customgallerypicker.AlbumPhotosActivity;
import com.intuz.customgallerypicker.R;
import com.intuz.customgallerypicker.Utils.GalleryPickerSingletone;
import com.intuz.customgallerypicker.model.AlbumData;

import java.util.List;

/**
 * Created by jack.m on 10/1/2016.
 * Adapter for Album List
 */
//  The MIT License (MIT)

//  Copyright (c) 2018 Intuz Solutions Pvt Ltd.

//  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
//  (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
//  merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:

//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.MyViewHolder> {
    private List<AlbumData> mAlbumList;
    private Activity mActivity;
    private GalleryPickerSingletone mGalleryPickerSingletone;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView albumTitle;
        public ImageView albumImage;
        public RelativeLayout selectedView;
        public TextView albumSelectedImageCount;

        public MyViewHolder(View view) {
            super(view);
            albumTitle = (TextView) view.findViewById(R.id.album_name);
            albumImage = (ImageView) view.findViewById(R.id.album_photo);
            selectedView = (RelativeLayout) view.findViewById(R.id.rl_selected_view);
            albumSelectedImageCount = (TextView) view.findViewById(R.id.album_selected_count);
        }
    }


    public AlbumGridAdapter(Activity activity,List<AlbumData> albumList,  GalleryPickerSingletone galleryPickerSingletone) {
        this.mAlbumList = albumList;
        this.mActivity = activity;
        this.mGalleryPickerSingletone = galleryPickerSingletone;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album_cards, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AlbumData albumData = mAlbumList.get(position);
        holder.albumTitle.setText(albumData.getAlbumName() + " (" + albumData.getAlbumImageCount() + ")");

        Glide.with(mActivity).load(albumData.getAlbumImageUri()).centerCrop().override(250, 250).into(holder.albumImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryPickerSingletone.setAlbumPos(position + 1);
                Intent albumIntent = new Intent(mActivity, AlbumPhotosActivity.class);
                albumIntent.putExtra(AlbumPhotosActivity.INTENT_ALBUM_ID, albumData.getAlbumId());
                albumIntent.putExtra(AlbumPhotosActivity.INTENT_ALBUM_NAME, albumData.getAlbumName());
                mActivity.startActivity(albumIntent);
            }
        });

        if (albumData.isAlbumImageSelected()) {
            if (albumData.getAlbumImageSelectedCount() > 0) {
                holder.selectedView.setVisibility(View.VISIBLE);
            }
            holder.albumSelectedImageCount.setText(albumData.getAlbumImageSelectedCount() + "");
           
        } else {
            holder.selectedView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}