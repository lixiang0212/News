package zhuoxin.com.viewpagerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import zhuoxin.com.viewpagerdemo.R;

public class JsonTestActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test);
        initView();
    }

    private void initView() {
        Log.i("AAA","AAA");
        new Thread(new Runnable() {
            public void run() {
                String httpUrl = "http://apis.baidu.com/txapi/tiyu/tiyu";
                String httpArg = "num=8&page=1";
                String jsonResult = request(httpUrl, httpArg);
                Log.i("AAA",jsonResult+"");

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            Log.i("AAA","code"+code);
            Log.i("AAA","msg"+msg);
            if(code==200) {
                JSONArray array = jsonObject.getJSONArray("newslist");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String time = object.getString("ctime");
                    String title = object.getString("title");
                    String description = object.getString("description");
                    String picUrl = object.getString("picUrl");
                    String url = object.getString("url");
                    Log.i("AAA", "ctime" + time);
                    Log.i("AAA", "title" + title);
                    Log.i("AAA", "description" + description);
                    Log.i("AAA", "picUrl" + picUrl);
                    Log.i("AAA", "url" + url);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
                }
            }).start();

    }
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "efb26bfc8b3931892fccbe42fc82496c");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                Log.i("AAA",strRead);
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
