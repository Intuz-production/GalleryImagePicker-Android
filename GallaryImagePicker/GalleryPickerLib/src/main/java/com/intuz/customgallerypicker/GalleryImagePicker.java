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


package com.intuz.customgallerypicker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.intuz.customgallerypicker.Utils.GalleryPickerSingletone;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryImagePicker {

    private int mMaxCount;
    private int mMinCount;
    private HashMap<String, ArrayList<String>> mImageSelectionAlbums;
    private onGalleryResultResponse mOnGallaryResultResponse;

    private GalleryPickerSingletone galleryPickerSingletone;


    public GalleryImagePicker(int maxCount, int minCount, HashMap<String, ArrayList<String>> imageSelectionAlbums) {
        this.mMaxCount = maxCount;
        this.mMinCount = minCount;
        if (imageSelectionAlbums != null) {
            this.mImageSelectionAlbums = imageSelectionAlbums;
        }

        galleryPickerSingletone = GalleryPickerSingletone.getInstance();

    }

    /*GalleryImagePicker constructor
      * maxCount = max selection limit
      * minCount = min selection limit
      */
    public GalleryImagePicker(int maxCount, int minCount) {
        this.mMaxCount = maxCount;
        this.mMinCount = minCount;
        galleryPickerSingletone = GalleryPickerSingletone.getInstance();
    }

    public GalleryImagePicker() {
        galleryPickerSingletone = GalleryPickerSingletone.getInstance();

    }

    /*Clear all available data
  */
    public void clearPhotos() {
        galleryPickerSingletone.clearData();
    }

    /*open Gallary
     * maxCount = max selection limit
      * minCount = min selection limit
  */
    public void openGallery(Context context, int maxCount, int minCount, onGalleryResultResponse onGallaryResultResponse) {

        if (mImageSelectionAlbums != null) {
            galleryPickerSingletone.setImageSelectionAlbums(mImageSelectionAlbums);
        }
        this.mMaxCount = maxCount;
        this.mMinCount = minCount;
        galleryPickerSingletone.setMaxCount(maxCount);
        galleryPickerSingletone.setMinCount(minCount);
        setOnGallaryResultResponse(onGallaryResultResponse);
        Intent i = new Intent(context, AlbumChooserActivity.class);
        context.startActivity(i);
    }

    public void openGallery(Context context, onGalleryResultResponse onGallaryResultResponse) {
        if (mImageSelectionAlbums != null) {
            galleryPickerSingletone.setImageSelectionAlbums(mImageSelectionAlbums);
        }
        galleryPickerSingletone.setMaxCount(mMaxCount);
        galleryPickerSingletone.setMinCount(mMinCount);
        setOnGallaryResultResponse(onGallaryResultResponse);
        Intent i = new Intent(context, AlbumChooserActivity.class);
        context.startActivity(i);
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
    }

    public int getMinCount() {
        return mMinCount;
    }

    public void setMinCount(int minCount) {
        this.mMinCount = minCount;
    }

    public void setOnGallaryResultResponse(onGalleryResultResponse onGalleryResultResponse) {
        this.mOnGallaryResultResponse = onGalleryResultResponse;
        galleryPickerSingletone.setOnGallaryResultResponse(onGalleryResultResponse);
    }

    public interface onGalleryResultResponse {

        public void onGalleryResult(ArrayList<Uri> selectedPics);
    }

}
