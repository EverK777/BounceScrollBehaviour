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
