package zhuoxin.com.viewpagerdemo.manager;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;

public class MyApplication extends Application {

    private  Gson gson;
    private  RequestQueue queue;
    public  RequestQueue getQueue() {
        return queue;
    }
    public  Gson getGson() {
        return gson;
    }
    private  static MyApplication myApplication;
    public static MyApplication getInstance(){
        return myApplication;
    }

    public void onCreate() {
        super.onCreate();
        gson = new Gson();
        queue = Volley.newRequestQueue(getApplicationContext());
        myApplication=this;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(2560,960)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
//                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(50)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
//                .imageDownloader(new BaseImageDownloader(this))//default
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
                .writeDebugLogs()
                .build();
        // 2.将配置信息给予我们的ImageLoader对象
        ImageLoader.getInstance().init(configuration);

    }

    public void onTerminate() {
        super.onTerminate();
        queue.cancelAll(this);

    }
}
