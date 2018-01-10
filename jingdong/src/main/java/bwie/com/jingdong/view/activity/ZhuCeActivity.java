package bwie.com.jingdong.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.ZhuCeBean;
import bwie.com.jingdong.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZhuCeActivity extends AppCompatActivity {
    private EditText zctel;
    private EditText zctel1;
    private EditText zcpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        zctel1 = (EditText) findViewById(R.id.zctel);
        zcpass = (EditText) findViewById(R.id.zcpass);

    }
    //注册按钮
    public void zczhuce(View view) {
        String tel = zctel1.getText().toString();
        String pass = zcpass.getText().toString();
        if(!TextUtils.isEmpty(tel)&&!TextUtils.isEmpty(pass)){
            if(isPhoneNumber(tel)&&pass.length()>=6){

                Map<String,String> params=new HashMap<>();
                params.put("mobile",tel);
                params.put("password",pass);
                OkHttp3Util.doPost("https://www.zhaoapi.cn/user/reg", params, new Callback() {
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
                                ZhuCeBean zhuCeBean = new Gson().fromJson(json, ZhuCeBean.class);
                                String code = zhuCeBean.getCode();
                                if("0".equals(code)){
                                    Toast.makeText(ZhuCeActivity.this,zhuCeBean.getMsg(),Toast.LENGTH_SHORT).show();
                                    ZhuCeActivity.this.finish();
                                }else if("1".equals(code))
                                {
                                    Toast.makeText(ZhuCeActivity.this,zhuCeBean.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

            }else if(!isPhoneNumber(tel))
            {
                Toast.makeText(ZhuCeActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            }else if(pass.length()<6)
            {
                Toast.makeText(ZhuCeActivity.this,"密码长度至少六位",Toast.LENGTH_SHORT).show();
            }
        }else if(TextUtils.isEmpty(tel))
        {
            Toast.makeText(ZhuCeActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(ZhuCeActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
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


    //返回登录页面
    public void fanhui(View view) {
        ZhuCeActivity.this.finish();
    }


}
