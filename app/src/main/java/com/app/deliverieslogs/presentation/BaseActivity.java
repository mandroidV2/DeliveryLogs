package com.app.deliverieslogs.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.deliverieslogs.DeliveryLogsApp;
import com.app.deliverieslogs.common.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/** Activity is used to perform the basic functionality for all the activities
 *  @author Manish Agrawal */
public abstract class BaseActivity extends AppCompatActivity {

    protected DeliveryLogsApp application;
    protected ImageLoader imageLoader;
    protected BaseActivity activity;
    protected DisplayImageOptions options;

    public BaseActivity() {
        super();
        options = Utils.displayImageOption();
    }

    /** Called when the activity is starting
     *  @param savedInstanceState
     *  @param layout layout resource id */
    protected final void onCreate(Bundle savedInstanceState, int layout) {
        super.onCreate(savedInstanceState);
        application = (DeliveryLogsApp) getApplication();
        activity = this;
        setContentView(layout);
    }

    /**
     * Returns the image loader object
     * @return
     */
    public ImageLoader getImageLoader() {
        // Initialize ImageLoader with configuration.
        if (imageLoader == null || !imageLoader.isInited()) {
            imageLoader = ImageLoader.getInstance();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    this).threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheSize(50 * 1024 * 1024)
                    .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            imageLoader.init(config);
        }
        return imageLoader;
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            imageLoader.resume();
        } catch (Exception e) {}
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            imageLoader.stop();
        } catch (Exception e) {}
    }
}
