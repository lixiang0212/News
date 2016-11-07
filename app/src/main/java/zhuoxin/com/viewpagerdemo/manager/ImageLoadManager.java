package zhuoxin.com.viewpagerdemo.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoadManager {
    private ImageView imageView;
    private String picUrl;
    private LruCache<String,Bitmap> lruCache;//相当于Map<KEY,VALUE>
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((imageView.getTag()).equals(picUrl)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
                Log.i("AAA", "setImage_threadOK");
            }
            else {
                Log.i("AAA", "setImage_threadNO");
            }
        }
    };

    public ImageLoadManager() {
        int max = (int)Runtime.getRuntime().maxMemory()/6;//获取手机的运行内存大小
        lruCache= new LruCache<String, Bitmap>(max){
        protected int sizeOf(String key, Bitmap value) {//必须重新
            return value.getByteCount();
            }};
    }
    public Bitmap getBitmapFromCache(String urlStrs) {
        return lruCache.get(urlStrs);//去缓存拿图片
    }
    public void addBitmapToCache(String urlStr, Bitmap bitmap) {
        if (getBitmapFromCache(urlStr) != null) {return;}
        else {
            lruCache.put(urlStr, bitmap);
        }
    }
    public Bitmap GetImage(final String picUrl) {
        Bitmap bitmap = getBitmapFromCache(picUrl);
        if(bitmap!=null){
            Log.i("AAA","缓存获取图片");
        }
        if(bitmap==null) {
            try {
                URL url = new URL(picUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.setReadTimeout(8000);
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(inputStream);
                addBitmapToCache(picUrl, bitmap);
                connection.disconnect();
                inputStream.close();
            } catch (MalformedURLException e) {
                Log.i("AAA", "MalformedURLException");
            } catch (IOException o) {
                Log.i("AAA", "IOException");
            }
            Log.i("AAA","网上获取图片");
        }
        return bitmap;
    }

    public void LoadImage(final String picUrl , ImageView imageView) {
        this.imageView = imageView;
        this.picUrl = picUrl;
        new Thread() {
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromCache(picUrl);
                if(bitmap==null){//先去缓存拿，如果没有就去网上加载
                   bitmap = GetImage(picUrl);
                   addBitmapToCache(picUrl,bitmap);//顺便加载到缓存空间
                    Log.i("AAA","网上获取图片");
                }
                else {
                    Log.i("AAA","缓存获取图片");
                }
                Message message = Message.obtain();
                message.obj=bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }
    public void LoadImageAsyncTask(final String picUrl , ImageView imageView) {
        this.imageView = imageView;
        this.picUrl = picUrl;
        new MyAsyncTask(imageView).execute(picUrl);
    }
    private class MyAsyncTask extends AsyncTask<String,Integer,Bitmap>{
        private ImageView imageView;

        public MyAsyncTask(ImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String... params) {
            String bitmapUrl = params[0];//获取picUrl
            Bitmap bitmap = getBitmapFromCache(bitmapUrl);
            if (bitmap == null) {
                bitmap = GetImage(bitmapUrl);
                addBitmapToCache(picUrl, bitmap);
            }
                return bitmap;
         }
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView.getTag().equals(picUrl)){
                imageView.setImageBitmap(bitmap);
                Log.i("AAA","setImageAsync-OK");
            }
            else {
                Log.i("AAA","setImageAsync-NO");
            }
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onCancelled(Bitmap bitmap) {
            super.onCancelled(bitmap);
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
