package zhuoxin.com.viewpagerdemo.activity;
//
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
//import zhuoxin.com.viewpagerdemo.R;
//import zhuoxin.com.viewpagerdemo.fragment.FragmentOne;
//import zhuoxin.com.viewpagerdemo.fragment.FragmentThree;
//import zhuoxin.com.viewpagerdemo.fragment.FragmentTwo;
//
public class FragMentActivity extends AppCompatActivity {
//
//    private Button btn_one,btn_two,btn_three;
//    private FragmentOne fragment_news;
//    private FragmentTwo fragment_hot;
//    private FragmentThree fragment_find;
//    private FragmentManager manager;
//    private FragmentTransaction transaction;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_frag_ment);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//
//        btn_one=(Button)findViewById(R.id.btn_one);
//        btn_two=(Button)findViewById(R.id.btn_two);
//        btn_three=(Button)findViewById(R.id.btn_three);
//        btn_one.setOnClickListener(this);
//        btn_two.setOnClickListener(this);
//        btn_three.setOnClickListener(this);
//
//    }
//
//    private void initData() {
//        fragment_news = new FragmentOne();
//        manager =getFragmentManager();
//        transaction =manager.beginTransaction();
//        transaction.replace(R.id.fragment_news,fragment_news);
//        transaction.commit();
//    }
//
//    public void onClick(View view) {
//
//        transaction = manager.beginTransaction();
//        switch (view.getId()){
//            case R.id.btn_one:
//                if(fragment_news == null){
//                    fragment_news = new FragmentOne();
//                }
//                transaction.replace(R.id.fragment_news,fragment_news);
//                break;
//            case R.id.btn_two:
//                if(fragment_hot == null){
//                    fragment_hot = new FragmentTwo();
//                }
//                transaction.replace(R.id.fragment_news,fragment_hot);
//                break;
//            case R.id.btn_three:
//                if(fragment_find== null){
//                    fragment_find = new FragmentThree();
//                }
//                transaction.replace(R.id.fragment_news,fragment_find);
//                break;
//        }
//        transaction.commit();
//    }
}
