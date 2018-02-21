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


package com.intuz.customgallerypicker.Utils;

import android.net.Uri;

import com.intuz.customgallerypicker.GalleryImagePicker;

import java.util.ArrayList;
import java.util.HashMap;


public class GalleryPickerSingletone {

    private static GalleryPickerSingletone instance = null;

    private int mMaxCount;
    private int mMinCount;
    private int mTotalSelectedImage;
    private int mAlbumPos;
    private ArrayList<Uri> mSelectedImageUri;
    private HashMap<String,ArrayList<String>> mImageSelectionAlbums;
    private GalleryImagePicker.onGalleryResultResponse mOnGalleryResultResponse;

    protected GalleryPickerSingletone() {
        // Exists only to defeat instantiation.
    }

    public static GalleryPickerSingletone getInstance() {
        if (instance == null) {
            instance = new GalleryPickerSingletone();
        }
        return instance;
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

    public void setAlbumPos(int albumPos) {
        this.mAlbumPos = albumPos;
    }

    public int getAlbumPos() {
        return mAlbumPos;
    }

    public void setImageSelectionAlbums(HashMap<String, ArrayList<String>> imageSelectionAlbums) {
        this.mImageSelectionAlbums = imageSelectionAlbums;
    }

    public HashMap<String, ArrayList<String>> getImageSelectionAlbums() {
        return mImageSelectionAlbums;
    }

    public void setTotalSelectedImage(int totalSelectedImage) {
        this.mTotalSelectedImage = totalSelectedImage;
    }

    public int getTotalSelectedImage() {
        return mTotalSelectedImage;
    }

    public void setSelectedImageUri(ArrayList<Uri> selectedImageUri) {
        this.mSelectedImageUri = selectedImageUri;
    }

    public ArrayList<Uri> getSelectedImageUri() {
        return mSelectedImageUri;
    }

    public void setOnGallaryResultResponse(GalleryImagePicker.onGalleryResultResponse onGallaryResultResponse) {
        this.mOnGalleryResultResponse = onGallaryResultResponse;
    }

    public GalleryImagePicker.onGalleryResultResponse getOnGallaryResultResponse() {
        return mOnGalleryResultResponse;
    }


    public void clearData(){
        mMaxCount = 0;
        mMinCount = 0;
        mTotalSelectedImage = 0;
        mAlbumPos = 0;
        mOnGalleryResultResponse = null;
        mImageSelectionAlbums = new HashMap<>();
        mSelectedImageUri.clear();

    }
}

