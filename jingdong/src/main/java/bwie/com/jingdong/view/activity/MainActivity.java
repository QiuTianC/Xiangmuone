package bwie.com.jingdong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import bwie.com.jingdong.R;


public class MainActivity extends AppCompatActivity {

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1)
            {
                Intent intent=new Intent(MainActivity.this, FristActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //页面跳转
        tiaozhuan();
    }

    private void tiaozhuan() {
        new Thread(){
            @Override
            public void run() {
                Message meg = Message.obtain();
                for (int j=0;j<3;j++)
                {
                    if(j==2)
                    {
                        meg.what=1;
                        handler.sendMessage(meg);
                    }else{
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }.start();
    }
}
