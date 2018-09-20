package com.app.deliverieslogs.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.params.CoreProtocolPNames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/** Class is used to contains the utility methods
 *  @author mandroid_v2.0 */
public class Utils {

    /** Method is used to set the options for the universal image loader
     *  @return DisplayImageOptions */
    public static DisplayImageOptions displayImageOption() {
        return new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).build();
    }

    /** Method is used to render image through universal image library
     *  @param imageLoader
     *  @param options
     *  @param imageUrl
     *  @param img */
    public static void renderImageUIL(ImageLoader imageLoader, DisplayImageOptions options,
            String imageUrl, ImageView img) {
        /** display the image */
        imageLoader.displayImage(imageUrl, img, options,
            new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {}
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    Log.e("Utils", "Error in loading Image for Uri : " + imageUri);
                }
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {}
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
