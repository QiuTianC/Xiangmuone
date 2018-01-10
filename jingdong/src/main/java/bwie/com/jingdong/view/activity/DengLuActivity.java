package bwie.com.jingdong.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.DengluBean;
import bwie.com.jingdong.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DengLuActivity extends AppCompatActivity {

    private ImageView img;
    private EditText tel;
    private EditText pwd;
    private TextView zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu);
        //获取控件
        img = (ImageView) findViewById(R.id.imgclick);
        tel = (EditText) findViewById(R.id.tel);
        pwd = (EditText) findViewById(R.id.pwd);
        zhuce = (TextView) findViewById(R.id.zhuce);

        //点击×号这张图片退出登录返回我的页面
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DengLuActivity.this.finish();
            }
        });
        //注册
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DengLuActivity.this,ZhuCeActivity.class);
                startActivity(intent);
            }
        });
    }

    //登录按钮
    public void dl(View view) {
        String phone = tel.getText().toString();
        String pass = pwd.getText().toString();
        if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass)){
            if(isPhoneNumber(phone)&&pass.length()>=6){
                Map<String,String> params=new HashMap<>();
                params.put("mobile",phone);
                params.put("password",pass);
                //params.put("token","andriod");
                OkHttp3Util.doPost("https://www.zhaoapi.cn/user/login", params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String json = null;
                                try {
                                    json = response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                DengluBean dengluBean = new Gson().fromJson(json, DengluBean.class);
                                String code = dengluBean.getCode();
                                if("0".equals(code)){
                                    SharedPreferences aa =DengLuActivity.this.getSharedPreferences("aa", DengLuActivity.this.MODE_WORLD_WRITEABLE);
                                    SharedPreferences.Editor edit = aa.edit();
                                    edit.putString("userimg",R.drawable.root+"");

                                    edit.putString("username",dengluBean.getData().getUsername());
                                    edit.putString("userid",dengluBean.getData().getUid()+"");
                                    edit.putString("icon",dengluBean.getData().getIcon()+"");
                                    edit.putBoolean("is",true);
                                    edit.commit();
                                    Toast.makeText(DengLuActivity.this,dengluBean.getMsg(),Toast.LENGTH_SHORT).show();
                                    DengLuActivity.this.finish();
                                }else if("1".equals(code))
                                {
                                    Toast.makeText(DengLuActivity.this,dengluBean.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });


            }else if(!isPhoneNumber(phone))
            {
                Toast.makeText(DengLuActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            }else if(pass.length()<6)
            {
                Toast.makeText(DengLuActivity.this,"密码长度至少六位",Toast.LENGTH_SHORT).show();
            }
        }else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(DengLuActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(DengLuActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    //判断是否是手机号
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }
}
