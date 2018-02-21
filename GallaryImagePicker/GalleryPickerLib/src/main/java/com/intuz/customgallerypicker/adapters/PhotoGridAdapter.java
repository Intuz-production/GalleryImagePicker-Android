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


package com.intuz.customgallerypicker.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.intuz.customgallerypicker.R;
import com.intuz.customgallerypicker.Utils.GalleryPickerSingletone;
import com.intuz.customgallerypicker.model.AlbumData;

import java.util.ArrayList;
import java.util.List;



public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.MyViewHolder> {
    private List<AlbumData> mAlbumList;
    private ArrayList<String> mSelectedImageList;
    private GalleryPickerSingletone mGalleryPickerSingletone;
    private Activity mActivity;
    private String mAlbumId;
    private ArrayList<Uri> mSelectedImageUri;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView albumImage;
        public RelativeLayout selectedView;

        public MyViewHolder(View view) {
            super(view);

            albumImage = (ImageView) view.findViewById(R.id.album_photo);
            selectedView = (RelativeLayout) view.findViewById(R.id.rl_selected_view);
        }
    }


    public PhotoGridAdapter(Activity activity,List<AlbumData> albumList, String albumId,  GalleryPickerSingletone galleryPickerSingletone) {
        this.mAlbumList = albumList;
        this.mActivity = activity;
        this.mAlbumId = albumId;
        this.mGalleryPickerSingletone = galleryPickerSingletone;
        mSelectedImageUri = galleryPickerSingletone.getSelectedImageUri();
        if (mSelectedImageUri == null) {
            mSelectedImageUri = new ArrayList<>();
        }
        mSelectedImageList = new ArrayList<>();
    }

    @Override
    public PhotoGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_card, parent, false);

        return new PhotoGridAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoGridAdapter.MyViewHolder holder, int position) {
        final AlbumData albumData = mAlbumList.get(position);

        if (albumData.isImageSelected()) {
            holder.selectedView.setVisibility(View.VISIBLE);
            if (!mSelectedImageList.contains(albumData.getAlbumSdcardPath())) {
                mSelectedImageList.add(albumData.getAlbumSdcardPath());
            }
        } else {
            holder.selectedView.setVisibility(View.GONE);
            if (mSelectedImageList.contains(albumData.getAlbumSdcardPath())) {
                mSelectedImageList.remove(albumData.getAlbumSdcardPath());
            }
        }

        Glide.with(mActivity).load(albumData.getAlbumImageUri()).centerCrop().override(250, 250).into(holder.albumImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkSelection(albumData);
            }
        });

    }

    public void checkSelection(AlbumData albumData) {
        if (albumData.isImageSelected()) {
            albumData.setImageSelected(false);
            mGalleryPickerSingletone.setTotalSelectedImage(mGalleryPickerSingletone.getTotalSelectedImage() - 1);
            if (mSelectedImageUri.contains(albumData.getAlbumImageUri())) {
                mSelectedImageUri.remove(albumData.getAlbumImageUri());
            }
        } else {

            if (mGalleryPickerSingletone.getMaxCount() > 0 && mGalleryPickerSingletone.getTotalSelectedImage() >= mGalleryPickerSingletone.getMaxCount()) {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.max_limit_exceed), Toast.LENGTH_LONG).show();
            } else {
                albumData.setImageSelected(true);

                mGalleryPickerSingletone.setTotalSelectedImage(mGalleryPickerSingletone.getTotalSelectedImage() + 1);
                if (!mSelectedImageUri.contains(albumData.getAlbumImageUri())) {
                    mSelectedImageUri.add(albumData.getAlbumImageUri());
                }
            }

        }

        mGalleryPickerSingletone.setSelectedImageUri(mSelectedImageUri);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    public ArrayList<String> getSelectedImageList() {
        return mSelectedImageList;
    }
}