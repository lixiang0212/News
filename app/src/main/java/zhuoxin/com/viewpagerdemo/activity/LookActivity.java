package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.CollectInfo;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;
import zhuoxin.com.viewpagerdemo.sql.MyCollectSQLiteUtil;

public class LookActivity extends AppCompatActivity {
    private String url;
    private String title;
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FloatingActionButton button;
    private MyCollectSQLiteUtil sqLiteUtil;
    private CoordinatorLayout layout;
    private LayoutInflater inflater;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
            if(msg.arg1==100){
                progressBar.setVisibility(View.GONE);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        inflater = LayoutInflater.from(this);
        ContentlistBean bean = (ContentlistBean) getIntent().getSerializableExtra("url");
        url = bean.getLink();
        title=bean.getTitle();
        initView();
        initData(url);

    }

    private void initView() {
        sqLiteUtil = new MyCollectSQLiteUtil(this);
        progressBar =(ProgressBar)findViewById(R.id.look_progressBar);
        layout= (CoordinatorLayout) findViewById(R.id.look_coordLayout);
        toolbar = (Toolbar) findViewById(R.id.activity_look_toolBar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.btn_return));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        button= (FloatingActionButton) findViewById(R.id.look_fabtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sqLiteUtil.insert(new CollectInfo(title,url));
               // layout.setTextAlignment();
                Snackbar.make(layout,"收藏成功!",Snackbar.LENGTH_INDEFINITE)
                        .setAction("查看收藏", new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(LookActivity.this,CollectActivity.class);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        })
                        .show();
              //  setSnackbarColor(snackbar,R.color.colorWhite,R.color.colorDeepSkyBlue);
            }
        });
    }

    private void initData(final String Url) {
        webView = (WebView) findViewById(R.id.activity_look_webView);
        webView.loadUrl(Url);
        webView.getSettings().setLoadWithOverviewMode(true);//back键返回
       // webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
    }
    class MyWebChromeClient extends WebChromeClient{
        public void onProgressChanged(WebView view, int newProgress) {
            //获取进度更新
            Message message = Message.obtain();
            message.arg1=newProgress;
            handler.sendMessage(message);
            super.onProgressChanged(view, newProgress);
        }
    }
    class MyWebViewClient extends WebViewClient{
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //拦截系统加载
            view.loadUrl(url);
            return true;
        }
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode){
//            //返回键的监听,返回上一页
//            case KeyEvent.KEYCODE_BACK:
//                Log.i("AAA","第一次");
//                if(webView.canGoBack()){
//                    Log.i("AAA","第二次");
//                    webView.goBack();
//                }
//            break;
//        }
//        return true;
//    }
    public  void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if(view!=null){
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
        }
    }
}
