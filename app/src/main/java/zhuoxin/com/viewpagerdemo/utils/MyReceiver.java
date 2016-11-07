package zhuoxin.com.viewpagerdemo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    public MyReceiver() {

    }
    public void onReceive(Context context, Intent intent) {
        Boolean flag = sharedAuto.get(context, "s_auto");
        if (flag) {
            if (intent.getAction().equals(ACTION)) {
                MyNotification.openNotification(context);
            }
        }
    }
}
