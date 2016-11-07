package zhuoxin.com.viewpagerdemo.interfaces;

import com.android.volley.VolleyError;

public interface VolleyInterface {

    void onSuccess(String result);
    void onFaild(VolleyError volleyError);
}
