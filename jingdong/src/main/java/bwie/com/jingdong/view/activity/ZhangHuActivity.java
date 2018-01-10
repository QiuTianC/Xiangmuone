package bwie.com.jingdong.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bwie.com.jingdong.R;

import static bwie.com.jingdong.R.id.userimg;
import static bwie.com.jingdong.R.id.username;

public class ZhangHuActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name;
    private LinearLayout zhangll;
    private String userid;
    private SharedPreferences aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hu);
        zhangll = (LinearLayout) findViewById(R.id.zhangll);
        img = (ImageView) findViewById(userimg);
        name = (TextView) findViewById(username);
        aa = getSharedPreferences("aa",MODE_PRIVATE);

        //修改账户信息
        zhangll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ZhangHuActivity.this,XiuGaiZhangHuActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userimg = aa.getString("userimg", null);
        String username = aa.getString("username", null);
        userid = aa.getString("userid", null);
        img.setImageResource(Integer.parseInt(userimg));
        name.setText(username);
    }

    //退出当前用户
    public void tuichu(View view) {
        SharedPreferences aa =getSharedPreferences("aa",MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor edit = aa.edit();
        edit.putBoolean("is",false);
        edit.commit();

        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(ZhangHuActivity.this);

        //    设置Content来显示一个信息
        builder.setMessage("确定退出登录？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ZhangHuActivity.this.finish();
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消",null);
        //    显示出该对话框
        builder.show();


    }

    public void fanhui(View view) {
        ZhangHuActivity.this.finish();
    }
}
