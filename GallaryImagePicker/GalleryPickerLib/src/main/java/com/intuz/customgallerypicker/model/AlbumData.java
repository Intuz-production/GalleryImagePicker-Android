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

package com.intuz.customgallerypicker.model;

import android.net.Uri;

public class AlbumData {

    private String albumName;

    private String albumId;

    private String albumImageCount;

    private String albumSdcardPath;

    private Uri albumImageUri;

    private String coverId;

    private int albumImageSelectedCount;

    private boolean albumImageSelected;

    private boolean isImageSelected;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumImageCount() {
        return albumImageCount;
    }

    public void setAlbumImageCount(String albumImageCount) {
        this.albumImageCount = albumImageCount;
    }

    public String getAlbumSdcardPath() {
        return albumSdcardPath;
    }

    public void setAlbumSdcardPath(String albumSdcardPath) {
        this.albumSdcardPath = albumSdcardPath;
    }

    public Uri getAlbumImageUri() {
        return albumImageUri;
    }

    public void setAlbumImageUri(Uri albumImageUri) {
        this.albumImageUri = albumImageUri;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public void setImageSelected(boolean imageSelected) {
        isImageSelected = imageSelected;
    }

    public boolean isImageSelected() {
        return isImageSelected;
    }

    public int getAlbumImageSelectedCount() {
        return albumImageSelectedCount;
    }

    public void setAlbumImageSelectedCount(int albumImageSelectedCount) {
        this.albumImageSelectedCount = albumImageSelectedCount;
    }

    public boolean isAlbumImageSelected() {
        return albumImageSelected;
    }

    public void setAlbumImageSelected(boolean albumImageSelected) {
        this.albumImageSelected = albumImageSelected;
    }
}
