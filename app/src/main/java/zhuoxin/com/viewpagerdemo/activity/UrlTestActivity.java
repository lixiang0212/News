package zhuoxin.com.viewpagerdemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import zhuoxin.com.viewpagerdemo.R;

public class UrlTestActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView iv;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                  webView.loadData((String)msg.obj,"text/html;charset=utf-8",null);
                    //iv.setImageBitmap((Bitmap) msg1.obj);
                    Log.i("AAA","shudao");
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_test);
        initView();
        //initData();
    }

    private void initData() {

        iv =(ImageView)findViewById(R.id.activity_url_iv);
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL("http://pic1.ooopic.com/uploadfilepic/sheji/2009-12-24/OOOPIC_xhpsjjz_20091224579fee5d118bfeeb.jpg");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    InputStream is = connection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Log.i("AAA","fileSDK");
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File parent = Environment.getExternalStorageDirectory();
                        File down = new File(parent, "one");
                        byte datas[] = new byte[1024 * 2];
                        int len = 0;
                        OutputStream os = new FileOutputStream(down);
                        BufferedOutputStream bos = new BufferedOutputStream(os);
                        while ((len = bis.read(datas)) != -1) {
                            Log.i("AAA", len + "");
                            bos.write(datas, 0, len);
                        }
                        bos.flush();
                        bos.close();
                        os.close();
                        bis.close();
                        is.close();
                        connection.disconnect();

                        Bitmap bitmap = BitmapFactory.decodeFile(down.getAbsolutePath());
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize=2;
//                        bitmap=BitmapFactory.decodeFile(down.getAbsolutePath(),options);
////                        bitmap.getWidth();
////                        bitmap.getHeight();
                        Log.i("AAA", "send" + down.getAbsolutePath());
                        Message msg1 = new Message();
                        msg1.what = 0x01;
                        msg1.obj = bitmap;
                        handler.sendMessage(msg1);
                    }
                } catch (MalformedURLException e) {
                    Log.i("AAA","AAA");
                }catch (IOException i){
                    Log.i("AAA","IO");
                }
            }
        }).start();
    }
    private void initView() {

        webView =(WebView)findViewById(R.id.activity_url_web);
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL("https://www.baidu.com");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    String datas =null;
                    while ((datas=br.readLine())!=null){
                        sb.append(datas);
                    }
                    Message msg = Message.obtain();
                    msg.what=0x01;
                    msg.obj =sb.toString();
                    handler.sendMessage(msg);
                    br.close();
                    isr.close();
                    is.close();
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException i){
                    Log.i("AAA","IO");
                }
            }
        }).start();
    }
}
