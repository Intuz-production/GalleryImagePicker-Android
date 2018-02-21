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

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.intuz.customgallerypicker.Utils.GalleryPickerSingletone;
import com.intuz.customgallerypicker.adapters.PhotoGridAdapter;
import com.intuz.customgallerypicker.model.AlbumData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumPhotosActivity extends AppCompatActivity {

    private RecyclerView rvPhotoGrid;
    private Toolbar toolbar;

    private GridLayoutManager gridLayoutManager;

    private List<AlbumData> mAlbumsList;
    private List<String> mSelectedPhotos;
    private PhotoGridAdapter mAdapter;
    private Cursor cursor;

    public static final String INTENT_ALBUM_ID = "intent_album_id";
    public static final String INTENT_ALBUM_NAME = "intent_album_name";

    private String mAlbumId;
    private String mAlbumName;

    private HashMap<String, ArrayList<String>> mImageSelectionAlbums;

    private GalleryPickerSingletone mGalleryPickerSingletone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photos);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGalleryPickerSingletone = mGalleryPickerSingletone.getInstance();
        mImageSelectionAlbums = mGalleryPickerSingletone.getImageSelectionAlbums();
        if (mImageSelectionAlbums == null) {
            mImageSelectionAlbums = new HashMap<>();
        }

        gridLayoutManager = new GridLayoutManager(AlbumPhotosActivity.this, 3);

        rvPhotoGrid = (RecyclerView) findViewById(R.id.recycler_view);
        rvPhotoGrid.setHasFixedSize(true);
        rvPhotoGrid.setLayoutManager(gridLayoutManager);

        mAlbumsList = new ArrayList<>();
        mAlbumId = getIntent().getStringExtra(INTENT_ALBUM_ID);
        mAlbumName = getIntent().getStringExtra(INTENT_ALBUM_NAME);
        if (mImageSelectionAlbums.containsKey(mAlbumId)) {
            mSelectedPhotos = mImageSelectionAlbums.get(mAlbumId);
        } else {
            mSelectedPhotos = new ArrayList<>();
        }


        getSupportActionBar().setTitle(mAlbumName);

        loadPhotos();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        } else if (menuItem.getItemId() == R.id.action_done) {
            refreshSelectedImages();
            finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    /*
    *Load available albums photos from device using album id
    *
    */
    private void loadPhotos() {
        mAlbumsList.clear();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                    String[] projection = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Media.BUCKET_ID};
                    final String selection = MediaStore.Images.Media.BUCKET_ID + " = ? AND (" + MediaStore.Images.Media.MIME_TYPE + " = ? OR " + MediaStore.Images.Media.MIME_TYPE + "=?)";
                    final String[] selectionArgs = {mAlbumId, "image/jpeg", "image/png"};
                    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    cursor = getContentResolver().query(images, projection, selection, selectionArgs, orderBy + " DESC");
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            AlbumData model = new AlbumData();
                            model.setAlbumSdcardPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                            model.setAlbumImageUri(Uri.fromFile(new File(model.getAlbumSdcardPath())));
                            boolean isSelected = false;
                            if (mSelectedPhotos.contains(model.getAlbumSdcardPath())) {
                                isSelected = true;
                            }

                            model.setImageSelected(isSelected);
                            mAlbumsList.add(model);
                        } while (cursor.moveToNext());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                            cursor = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {

                mAdapter = new PhotoGridAdapter(AlbumPhotosActivity.this, mAlbumsList, mAlbumId, mGalleryPickerSingletone);
                rvPhotoGrid.setAdapter(mAdapter);
            }

        }.execute();
    }


    /*
    *set selection images to singletone
    *
    */
    public void refreshSelectedImages() {
        if (mAdapter != null) {
            mImageSelectionAlbums.put(mAlbumId, mAdapter.getSelectedImageList());
            mGalleryPickerSingletone.setImageSelectionAlbums(mImageSelectionAlbums);
        }

    }


}
