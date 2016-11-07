package zhuoxin.com.viewpagerdemo.my_tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import com.nineoldandroids.view.ViewHelper;//第三方
import zhuoxin.com.viewpagerdemo.R;

public class myViewGroup extends HorizontalScrollView {

    // Menu布局
    private ViewGroup menu;
    // 主体布局
    private ViewGroup content;
    // Menu的宽度
    private int menuWidth;
    // 屏幕的宽度
    private int screenWidth;
    // 滑动到多大的距离需要呈现我们的Menu
    private int showMenuWidth;
    // 距离屏幕右侧的边距
    private int menuRightWidth;
    // 是否是第一次使用
    private boolean isOnce ;
    public myViewGroup(Context context) {
        this(context,null);
    }

    public myViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public myViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels; // 拿到屏幕的宽度
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.myViewGroup);
        menuRightWidth = typedArray.getDimensionPixelSize(R.styleable.myViewGroup_menuRigthWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50F,getResources().getDisplayMetrics()));
        typedArray.recycle();
        // menuRigthWidth = 100; // px
    }

    /**
     计算View的宽度和高度 （子布局 和 子控件 自己）
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!isOnce){ // 确保只会初始化一次
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            menu = (ViewGroup) viewGroup.getChildAt(0);
            content = (ViewGroup) viewGroup.getChildAt(1);
            menuWidth = screenWidth - menuRightWidth;
            showMenuWidth = menuWidth / 2;
            menu.getLayoutParams().width = menuWidth;
            content.getLayoutParams().width = screenWidth;
            isOnce = true;
        }
    }

    /**
     * 确定 子控件的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 隐藏 没有滑动效果
        super.scrollTo(menuWidth,0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();// 拿到滑动的距离
                if(scrollX >= showMenuWidth){
                    super.smoothScrollTo(menuWidth,0);// 带有滑动效果的 移动
                }else{
                    super.smoothScrollTo(0,0);
                }
                return true;
        }
        return super.onTouchEvent(ev);// 注意一定要重新调用父类
    }


    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0F / menuWidth; //   0 - 1
        float contentScale = 0.8F + scale * 0.2F; //  0.8 - 1 主布局
        float menuScale = 1 - scale * 0.2F;  // 1 - 0.8 菜单的缩放
        float menuAlpha = 0.5f +(1 - scale)  * 0.5F; // 0.5 - 1 菜单在滑动过程中 半显示 到全部显示
        // 开始缩放
        ViewHelper.setScaleX(menu,menuScale);
        ViewHelper.setScaleY(menu,menuScale);
        // 开始渐变
        ViewHelper.setAlpha(menu,menuAlpha);
        // 开始移动
        ViewHelper.setTranslationX(menu,menuWidth * scale * 0.8F);
        // 缩放轴心
        ViewHelper.setPivotX(content,0);
        ViewHelper.setPivotY(content,this.getHeight() / 2 );
        ViewHelper.setScaleY(content,contentScale);
        ViewHelper.setScaleY(content,contentScale);

    }

    public int getMenuRightWidth() {
        return menuRightWidth;
    }

    public void setMenuRightWidth(int menuRightWidth) {
        this.menuRightWidth = menuRightWidth;
    }
}
