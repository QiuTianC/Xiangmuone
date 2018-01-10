package bwie.com.jingdong.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.CountPriceBean;
import bwie.com.jingdong.utils.OkHttp3Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QueRenActivity extends AppCompatActivity {

    private TextView text_kuai;
    private TextView text_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ren);
        final CountPriceBean countPriceBean= (CountPriceBean) getIntent().getSerializableExtra("order");
        text_kuai = (TextView) findViewById(R.id.text_kuan);
        text_order = (TextView) findViewById(R.id.text_order);

        text_kuai.setText("实付款:￥"+countPriceBean.getPrice());
        text_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttp3Util.doGet("https://www.zhaoapi.cn/product/createOrder?uid=4084&price=" + countPriceBean.getPrice(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String json = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(QueRenActivity.this, json,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
