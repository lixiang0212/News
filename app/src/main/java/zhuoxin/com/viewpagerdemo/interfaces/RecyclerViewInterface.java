package zhuoxin.com.viewpagerdemo.interfaces;

import android.view.View;

public interface RecyclerViewInterface {
    void OnItemClickListener(View view,int position);
    void OnLongClickListener(View view,int position);
}
