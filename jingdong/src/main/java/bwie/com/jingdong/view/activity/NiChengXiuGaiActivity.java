package bwie.com.jingdong.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.GaiNiChengBean;
import bwie.com.jingdong.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NiChengXiuGaiActivity extends AppCompatActivity {

    private EditText gainame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ni_cheng_xiu_gai);
        gainame = (EditText) findViewById(R.id.gainame);

    }


    //返回上一页
    public void fanhui(View view) {
        NiChengXiuGaiActivity.this.finish();
    }
    //确定修改
    public void queding(View view) {
        String uid = getIntent().getStringExtra("user");

        if(gainame.getText().toString().length()>4||gainame.getText().toString().length()<20)
        {
            Map<String,String> map=new HashMap<>();
            map.put("uid",uid+"");
            map.put("nickname",gainame.getText().toString());
            map.put("token","android");
            OkHttp3Util.doPost("https://www.zhaoapi.cn/user/updateNickName", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    final GaiNiChengBean gaiNiChengBean = new Gson().fromJson(string, GaiNiChengBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String code = gaiNiChengBean.getCode();

                            if("1".equals(code)){
                                SharedPreferences aa =getSharedPreferences("aa",MODE_WORLD_WRITEABLE);
                                SharedPreferences.Editor edit = aa.edit();
                                edit.putString("username",gainame.getText().toString());
                                edit.commit();
                                NiChengXiuGaiActivity.this.finish();
                            }else
                            {
                                Toast.makeText(NiChengXiuGaiActivity.this,gaiNiChengBean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
        }else
        {
            Toast.makeText(NiChengXiuGaiActivity.this,"昵称不符合要求！",Toast.LENGTH_SHORT).show();
        }

    }
}
