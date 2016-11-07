package zhuoxin.com.viewpagerdemo.manager;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ConnectionManager {

    public static final String API_URL = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static final String API_KEY="efb26bfc8b3931892fccbe42fc82496c";

    public static String setName(String name,int page){
//        return API_URL+"?channelName="+URDecoter(name)+"&page="+page;
        return API_URL+"?title="+URDecoter(name)+"&page="+page;
    }

    public static String URDecoter(String name) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.i("AAA", "UTF=NO");
        }
        return name;
    }
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
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
