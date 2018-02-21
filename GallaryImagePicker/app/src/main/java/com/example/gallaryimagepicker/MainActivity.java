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

package com.example.gallaryimagepicker;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intuz.customgallerypicker.GalleryImagePicker;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private GalleryImagePicker mGallaryImagePicker = new GalleryImagePicker();
    private GalleryImagePicker.onGalleryResultResponse mOnGalleryResultResponse = new GalleryImagePicker.onGalleryResultResponse() {
        @Override
        public void onGalleryResult(ArrayList<Uri> selectedPics) {
            for(int i = 0; i <selectedPics.size();i++)
            {
                sb .append((i+1)+")"+selectedPics.get(i).getPath());
                sb.append("\n");

            }
            txtUrls.setText(sb.toString());
        }
    };
    private Button btn_Gallery;
    private TextView txtUrls;
    StringBuffer sb =new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Gallery = findViewById(R.id.btn_Gallery);
        txtUrls = findViewById(R.id.txtUrls);
        btn_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             mGallaryImagePicker.openGallery(MainActivity.this, mOnGalleryResultResponse);
                // With min max
                //mGallaryImagePicker.openGallery(MainActivity.this, maxcount, mincount, mOnGalleryResultResponse);
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGallaryImagePicker.clearPhotos();
    }

}
