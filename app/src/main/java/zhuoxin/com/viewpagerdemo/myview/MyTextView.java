package zhuoxin.com.viewpagerdemo.myview;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import zhuoxin.com.viewpagerdemo.interfaces.OnClickInterface;

public class MyTextView extends TextView {

    private boolean isSelect;
    private int position;
    private OnClickInterface face;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        // 监听到更改以后调用相应的方法 这个时候并不知道调用的方法具体是谁
        if(face!=null)
            face.OnClickChanged(select,this);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public OnClickInterface getFace() {
        return face;
    }

    public void setFace(OnClickInterface face) {
        this.face = face;
    }

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
