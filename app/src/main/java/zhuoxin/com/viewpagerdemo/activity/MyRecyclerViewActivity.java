package zhuoxin.com.viewpagerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.adapter.DividerItemDecoration;
import zhuoxin.com.viewpagerdemo.adapter.MyRecyclerViewAdapter;
import zhuoxin.com.viewpagerdemo.interfaces.RecyclerViewInterface;

public class MyRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> datas;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager manager;
    private MyRecyclerViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        initView();
    }
    private void initView() {
        toolbar=(Toolbar)findViewById(R.id.my_recycler_toolbar);
        toolbar.setTitle("呵呵");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        initData();
        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
//        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        manager =new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyRecyclerViewAdapter(this,datas);
        adapter.setViewInterface(new RecyclerViewInterface() {
            public void OnItemClickListener(View view, int position) {
                Toast.makeText(MyRecyclerViewActivity.this,"position-"+position,Toast.LENGTH_SHORT).show();
                adapter.remove(position);
            }

            public void OnLongClickListener(View view, int position) {
                Toast.makeText(MyRecyclerViewActivity.this,"Lposition-"+position,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void initData(){
        datas = new ArrayList<>();
        for(int i=1;i<31;i++){
            datas.add("王尼玛"+i+"号");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_toolbar_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case   R.id.recycle_icon_one:
                manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                break;
            case   R.id.recycle_icon_two:
                manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
                break;
            case   R.id.recycle_icon_three:
                manager =new  GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);
                break;
            case   R.id.recycle_icon_four:
                manager =new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
                break;
            case   R.id.recycle_toolbar_add:
                datas.add(1,"王尼玛*号");
                adapter.notifyItemInserted(1);
                break;
            case   R.id.recycle_toolbar_delete:
                datas.remove(1);
                adapter.notifyItemRemoved(1);
                break;
        }
        recyclerView.setLayoutManager(manager);
        return true;
    }
}
