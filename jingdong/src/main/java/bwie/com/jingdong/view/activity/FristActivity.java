package bwie.com.jingdong.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import bwie.com.jingdong.R;
import bwie.com.jingdong.view.activity.fragment.FaXianFragment;
import bwie.com.jingdong.view.activity.fragment.FenLeiFragment;
import bwie.com.jingdong.view.activity.fragment.GouWuCheFragment;
import bwie.com.jingdong.view.activity.fragment.ShouYeFragment;
import bwie.com.jingdong.view.activity.fragment.WoDeFragment;

public class FristActivity extends AppCompatActivity {


    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        rg = (RadioGroup) findViewById(R.id.rg);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new ShouYeFragment()).commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new ShouYeFragment()).commit();
                        break;
                    case R.id.rb2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FenLeiFragment()).commit();
                        break;
                    case R.id.rb3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FaXianFragment()).commit();
                        break;
                    case R.id.rb4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new GouWuCheFragment()).commit();
                        break;
                    case R.id.rb5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new WoDeFragment()).commit();
                        break;
                }
            }
        });
    }

}
