package zhuoxin.com.viewpagerdemo.manager;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.interfaces.VolleyInterface;

public class VolleyManager {
    private VolleyInterface volleyInterface;
    private LruCache<String,Bitmap> lruCache;
    public VolleyInterface getVolleyInterface() {
        return volleyInterface;
    }

    public void setVolleyInterface(VolleyInterface volleyInterface) {
        this.volleyInterface = volleyInterface;
    }

    private VolleyManager(){

    }
    private static VolleyManager manager;
    public static VolleyManager getVolleyManager(){
        if(manager==null){
            manager= new VolleyManager();
        }
        return manager;
    }
    public void getStringRequest(final String title){
        RequestQueue queue = MyApplication.getInstance().getQueue();
        StringRequest request =new StringRequest(Request.Method.GET,
               ConnectionManager.setName("国内最新",1), new Response.Listener<String>() {
            public void onResponse(String jsonResult) {
                Log.i("AAA",jsonResult);
                if(volleyInterface!=null){
                    volleyInterface.onSuccess(jsonResult);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("AAA",volleyError.toString());
                if(volleyInterface!=null){
                    volleyInterface.onFaild(volleyError);
                }
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> apikey = new HashMap<>();
                apikey.put("apikey",ConnectionManager.API_KEY);
                return apikey;
            }
        };
        request.setTag(title);
        queue.add(request);
    }
    public  void getImageRequest(String picUrl, final ImageView imageView){
        RequestQueue queue =MyApplication.getInstance().getQueue();
        ImageRequest request = new ImageRequest(picUrl, new Response.Listener<Bitmap>() {
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Log.i("AAA","imageSet-onResponse");
            }
        }, 2560, 1440, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.drawable.login_defult_img);
                Log.i("AAA","imageSet-onErrorResponse");
            }
        });
        request.setTag(picUrl);
        queue.add(request);
    }
    public void getImageLruCahe(String picUrl,ImageView imageView){
        ImageLoader loader = new ImageLoader(MyApplication.getInstance().getQueue(), new MyLruCahe());

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.drawable.find_defult,
                R.drawable.img_news_lodinglose);

        loader.get(picUrl,listener);
    }

    public class MyLruCahe implements ImageLoader.ImageCache{

        public MyLruCahe() {
            int maxSize = (int)Runtime.getRuntime().maxMemory()/20;
            lruCache = new LruCache<String, Bitmap>(maxSize){
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };}
        public Bitmap getBitmap(String key) {
            return lruCache.get(key);
        }
        public void putBitmap(String key, Bitmap bitmap) {
            if(key!=null&&bitmap!=null){
                lruCache.put(key, bitmap);

            }
        }}

}
