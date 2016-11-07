package zhuoxin.com.viewpagerdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.userInfo;
import zhuoxin.com.viewpagerdemo.sql.SQLiteAdd;

public class LoginActivity extends AppCompatActivity {
    private SQLiteAdd add;
    private EditText et_name,et_passwd;
    private Button bt_login,bt_zhuce;
    private boolean flag = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        add = new SQLiteAdd(this);
        et_name=(EditText)findViewById(R.id.activity_login_et_number);
        et_passwd=(EditText)findViewById(R.id.activity_login_et_password);
        bt_login=(Button)findViewById(R.id.activity_login_btn_login);
        bt_zhuce=(Button)findViewById(R.id.activity_login_btn_zhuce);
        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String passwd = et_passwd.getText().toString();
                if(!name.equals("")&&!passwd.equals("")) {
                    List<userInfo> list = add.queryAll();
                    int k = 0;
                    while (flag) {
                        for (int i = 0; i < list.size(); i++) {
                            if (!name.equals(list.get(i).getName())||!passwd.equals(list.get(i).getPasswd())) {
                                flag = true;
                                k++;
                            } else {
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                et_name.setText("");
                                et_passwd.setText("");
                                Intent intent = new Intent(LoginActivity.this, TitleActivity.class);
                                startActivity(intent);
                                finish();
                                flag = false;
                                break;
                            }
                        }
                        if(k>=list.size()){
                            flag=false;
                            Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                        }
                        et_name.setText("");
                        et_passwd.setText("");
                    }
                    flag=true;
                }
            }
        });
        bt_zhuce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ZhuceActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(resultCode==101){
                if(requestCode==100){
                    String name = data.getStringExtra("name");
                    String passwd = data.getStringExtra("passwd");
                    et_name.setText(name);
                    et_passwd.setText(passwd);
                }
            }
        }
    }
}