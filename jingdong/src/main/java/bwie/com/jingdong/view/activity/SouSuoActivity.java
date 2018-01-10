package bwie.com.jingdong.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import bwie.com.jingdong.R;
import bwie.com.jingdong.view.activity.fragment.SouSuo_1Fragment;
import bwie.com.jingdong.view.activity.fragment.SouSuo_2Fragment;

public class SouSuoActivity extends AppCompatActivity {


    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);

        name = (EditText) findViewById(R.id.name);


        //搜索框

        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new SouSuo_1Fragment()).commit();

    }

    public void add(View view) {
        String data = name.getText().toString();
        if(data.length()==0)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl,new SouSuo_1Fragment()).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl,new SouSuo_2Fragment()).commit();
        }
    }

    public void soufanhui(View view) {
        SouSuoActivity.this.finish();
    }
}
