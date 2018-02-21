<h1>Introduction</h1>
INTUZ is presenting an interesting  Gallery Image Picker component to integrate inside your Native Android based application. 
Gallery Image Picker is a simple component, which lets you pick a multiple images from your mobile gallery. 

Please follow the below steps to integrate this control in your project.

<br/><br/>
<h1>Features</h1>

- Easy & Fast to integrate.
- Can pick multiple images at a time.
- Can provide minimum and maximum image selection limit.


<img src="Screenshots/gallerypicker.gif" width="300" alt="Screenshots/Screen1.png">

<br/><br/>
<h1>Getting Started</h1>

To use this component in your project you need to perform below steps:

- Import this library as module in your project
- Add dependency of this module in your app build.gradle file
- Add android:configChanges="orientation" with your activity in your AndroidManifest.xml


1) Create Instance of GalleryImagePicker
```
    private GalleryImagePicker mGallaryImagePicker = new GalleryImagePicker();
```
2) Start Image Picker on click of any view as:

Without any restrictions.
```
 mGallaryImagePicker.openGallery(MainActivity.this, mOnGalleryResultResponse);
 ```
With Max and Min image selection limitation. Default is 0.
 ```               
 mGallaryImagePicker.openGallery(MainActivity.this, maxcount, mincount, mOnGalleryResultResponse);
 ```
 3) Implement listner as below to get list of selected image path:
 ```
 private GalleryImagePicker.onGalleryResultResponse mOnGalleryResultResponse = new GalleryImagePicker.onGalleryResultResponse() {
        @Override
        public void onGalleryResult(ArrayList<Uri> selectedPics) {
            // Use Selected image from here.
        }
    };
```
<br/><br/>
<h1>Bugs and Feedback</h1>
For bugs, questions and discussions please use the Github Issues.

<br/><br/>
<h1>License</h1>
The MIT License (MIT)
<br/><br/>
Copyright (c) 2018 INTUZ
<br/><br/>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: 
<br/><br/>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

<br/>
<br/>
<h1></h1>
<a href="https://www.intuz.com/" target="_blank"><img src="Screenshots/logo.jpg"></a>

