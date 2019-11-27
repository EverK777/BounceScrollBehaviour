# BounceScrollBehaviour

If you want to have scroll behaviour like IOS in your Android applications, this is the library for you.


## OverView

### Android Behaviour


![alt text](https://github.com/EverK777/Images/blob/master/android_image.gif) 


### IOS Behaviour


![alt text](https://github.com/EverK777/Images/blob/master/lib_image.gif)
                                                               

## Installation
### Configure jitpack in gradle
 
```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

### Add library to gradle app level and Sync

```
dependencies {
    implementation 'com.github.EverK777:BounceScrollBehaviour:1.0.1'
}
```

### Add the bouncing recyclerview like you normally add a recyclerView
 
 ```
     <com.ever777.bouncingscroll.BouncingRecyclerView
        android:id="@+id/horizontalRecycler"
        android:layout_width="match_parent"
        android:layout_marginTop="15dp"
        android:layout_below="@id/tittle_horizontal"
        android:layout_height="wrap_content"/>
 ```
 
 
 ### You can use NestedScrollView too 
 
 ```
 <com.ever777.bouncingscroll.BouncingNestedScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Nombre"/>
        </LinearLayout>
    </com.ever777.bouncingscroll.BouncingNestedScrollView>
 ```
 
# License
 
  ```
 MIT License

Copyright (c) 2019 EverK777

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 ```
