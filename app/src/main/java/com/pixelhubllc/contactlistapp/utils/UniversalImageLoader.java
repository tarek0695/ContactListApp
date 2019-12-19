package com.pixelhubllc.contactlistapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pixelhubllc.contactlistapp.R;

public class UniversalImageLoader {
    private static int defaultImage = R.drawable.ic_android;
    private Context mcontext;

    public UniversalImageLoader(Context mcontext) {
        this.mcontext = mcontext;
    }

    public ImageLoaderConfiguration getConfig(){
        //universal imageloader setup
        DisplayImageOptions defaultOption = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mcontext)
//                .defaultDisplayImageOptions(defaultOption)
//                .memoryCache(new WeakMemoryCache())
//                .diskCacheSize(50 * 1024 * 1024).build();

        return new ImageLoaderConfiguration.Builder(mcontext)
                .defaultDisplayImageOptions(defaultOption)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(50 * 1024 * 1024).build();
    }

    public static void setImage(String imgURL, ImageView imageView, final ProgressBar mProgressBar, String append){

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imgURL, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                if (mProgressBar!=null){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (mProgressBar!=null){
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (mProgressBar!=null){
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (mProgressBar!=null){
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
