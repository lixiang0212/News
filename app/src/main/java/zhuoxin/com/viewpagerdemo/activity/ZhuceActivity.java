package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.userInfo;
import zhuoxin.com.viewpagerdemo.sql.MySQLiteOpenHelper;
import zhuoxin.com.viewpagerdemo.sql.SQLiteAdd;

public class ZhuceActivity extends AppCompatActivity {

    private Button btn1;
    private EditText et1;
    private EditText et2;
    private SQLiteAdd add;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        initView();
    }

    private void initView() {
        add= new SQLiteAdd(this);
        et1 = (EditText) findViewById(R.id.etz1);
        et2 = (EditText) findViewById(R.id.etz2);
        btn1 = (Button) findViewById(R.id.butzl);
        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String name = et1.getText().toString();
                String passwd = et2.getText().toString();
                if(!name.equals("")&&!passwd.equals("")){
                    add.insert(new userInfo(name,passwd));
                    Toast.makeText(ZhuceActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("passwd", passwd);
                    setResult(101,intent);
                    finish();
                    et1.setText("");
                    et2.setText("");
                }
                else{
                    Toast.makeText(ZhuceActivity.this, "请输入有效的账户和密码!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
