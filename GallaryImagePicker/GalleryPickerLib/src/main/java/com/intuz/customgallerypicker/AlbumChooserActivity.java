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

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.intuz.customgallerypicker.Utils.GalleryPickerSingletone;
import com.intuz.customgallerypicker.adapters.AlbumGridAdapter;
import com.intuz.customgallerypicker.model.AlbumData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AlbumChooserActivity extends AppCompatActivity {


    private RecyclerView rvAlbumGrid;
    private Toolbar toolbar;

    private GridLayoutManager gridLayoutManager;

    private List<AlbumData> mAlbumsList;
    private AlbumGridAdapter mAdapter;
    private Cursor cursor;

    private List<String> mSelectedPhotos;
    private GalleryPickerSingletone mGalleryPickerSingletone;
    private HashMap<String, ArrayList<String>> mImageSelectionAlbums;

    private final int ACCESS_STORAGE_PERMISSION_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_chooser);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGalleryPickerSingletone = GalleryPickerSingletone.getInstance();
        mImageSelectionAlbums = mGalleryPickerSingletone.getImageSelectionAlbums();
        mGalleryPickerSingletone.setAlbumPos(0);

        if (mImageSelectionAlbums == null) {
            mImageSelectionAlbums = new HashMap<>();
        }
        gridLayoutManager = new GridLayoutManager(AlbumChooserActivity.this, 2);

        rvAlbumGrid = (RecyclerView) findViewById(R.id.recycler_view);
        rvAlbumGrid.setHasFixedSize(true);
        rvAlbumGrid.setLayoutManager(gridLayoutManager);

        mAlbumsList = new ArrayList<>();
        mSelectedPhotos = new ArrayList<>();

        if (checkAcessStoragePermissions()) {
            loadAlbum();
        }

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
            if (isMinimumDone()) {
                mGalleryPickerSingletone.getOnGallaryResultResponse().onGalleryResult(mGalleryPickerSingletone.getSelectedImageUri());
                finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.please_select_minimum_image), Toast.LENGTH_LONG).show();
            }
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    /*
    *Load available albums from device
    *
    */
    private void loadAlbum() {
        mAlbumsList.clear();
        mGalleryPickerSingletone.setTotalSelectedImage(0);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String[] PROJECTION_BUCKET = {MediaStore.Images.ImageColumns.BUCKET_ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATA};
                    String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
                    String BUCKET_ORDER_BY = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " COLLATE NOCASE ASC";
                    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    cursor = getContentResolver().query(images, PROJECTION_BUCKET, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

                    if (cursor.moveToFirst()) {
                        do {
                            String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                            String[] projection1 = new String[]{MediaStore.Images.Thumbnails.DATA};
                            final String selection = MediaStore.Images.Media.BUCKET_ID + " = ? AND (" + MediaStore.Images.Media.MIME_TYPE + " = ? OR " + MediaStore.Images.Media.MIME_TYPE + "=?)";
                            final String[] selectionArgs = {bucketId, "image/jpeg", "image/png"};
                            Uri images1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                            Cursor cursor1 = null;
                            try {

                                cursor1 = getContentResolver().query(images1, projection1, selection, selectionArgs, MediaStore.Images.Media._ID);
                                if (cursor1.moveToFirst()) {
                                    AlbumData album = new AlbumData();
                                    album.setAlbumId(bucketId);
                                    album.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                                    album.setAlbumImageCount(String.valueOf(cursor1.getCount()));
                                    album.setAlbumSdcardPath(cursor1.getString(cursor1.getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
                                    album.setAlbumImageUri(Uri.fromFile(new File(album.getAlbumSdcardPath())));

                                    if (mImageSelectionAlbums.containsKey(bucketId)) {
                                        album.setAlbumImageSelected(true);
                                        album.setAlbumImageSelectedCount(mImageSelectionAlbums.get(bucketId).size());
                                        mGalleryPickerSingletone.setTotalSelectedImage(mGalleryPickerSingletone.getTotalSelectedImage() + album.getAlbumImageSelectedCount());
                                    } else {
                                        album.setAlbumImageSelected(false);
                                        album.setAlbumImageSelectedCount(0);
                                    }
                                    mAlbumsList.add(album);
                                }

                            } catch (Throwable e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (cursor1 != null && !cursor1.isClosed()) {
                                        cursor1.close();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } while (cursor.moveToNext());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (!cursor.isClosed()) {
                            cursor.close();
                        }
                        cursor = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {

                mAdapter = new AlbumGridAdapter(AlbumChooserActivity.this,mAlbumsList, mGalleryPickerSingletone);
                rvAlbumGrid.setAdapter(mAdapter);


            }

        }.execute();
    }

    @Override
    protected void onResume() {
       // mGalleryPickerSingletone.setTotalSelectedImage(0);
        if (mAlbumsList != null) {
            if (mGalleryPickerSingletone.getAlbumPos() != 0) {
                AlbumData album = mAlbumsList.get(mGalleryPickerSingletone.getAlbumPos() - 1);
                mImageSelectionAlbums = mGalleryPickerSingletone.getImageSelectionAlbums();
                if (mImageSelectionAlbums != null) {
                    if (mImageSelectionAlbums.containsKey(album.getAlbumId())) {
                        int totalSize = mImageSelectionAlbums.get(album.getAlbumId()).size();
                        if (totalSize > 0) {
                            album.setAlbumImageSelected(true);
                            album.setAlbumImageSelectedCount(totalSize);
                           // mGalleryPickerSingletone.setTotalSelectedImage(mGalleryPickerSingletone.getTotalSelectedImage() + album.getAlbumImageSelectedCount());
                        } else {
                            album.setAlbumImageSelected(false);
                            album.setAlbumImageSelectedCount(0);
                        }

                    }
                }
                mAdapter.notifyDataSetChanged();
            }

        }
        super.onResume();
    }


    /*
    *Check if minimum value is set then selection must >= minimum count
    *
    */

    public boolean isMinimumDone() {
        int minimum = mGalleryPickerSingletone.getMinCount();
        if (minimum > 0 && minimum > mGalleryPickerSingletone.getTotalSelectedImage()) {
            return false;
        }

        return true;
    }


    /*
    *Check storage permission
    *
    */
    private boolean checkAcessStoragePermissions() {
        int permissionReadStorage = ContextCompat.checkSelfPermission(AlbumChooserActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(AlbumChooserActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), ACCESS_STORAGE_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("iii", "Permission callback called-------");
        switch (requestCode) {
            case ACCESS_STORAGE_PERMISSION_REQUEST_CODE: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("ii", "READ_EXTERNAL_STORAGE  services permission granted");
                        loadAlbum();
                    } else {
                        Log.d("ii", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(AlbumChooserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK(getResources().getString(R.string.read_external_permission_require),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAcessStoragePermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(AlbumChooserActivity.this, getResources().getString(R.string.go_setting_enable_permission), Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AlbumChooserActivity.this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), okListener)
                .create()
                .show();
    }
}
