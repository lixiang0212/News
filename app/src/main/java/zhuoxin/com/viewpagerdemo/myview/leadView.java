package zhuoxin.com.viewpagerdemo.myview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import zhuoxin.com.viewpagerdemo.R;

public class leadView extends View{

    private Paint paint;
    private int color;
    private float x=25,y=25,r=25;

    public leadView(Context context) {
        this(context,null);
    }

    public leadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public leadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.leadView);
        color = array.getColor(R.styleable.leadView_color,context.getResources().getColor(R.color.colorAccent));
        array.recycle();
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x,y,r,paint);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
