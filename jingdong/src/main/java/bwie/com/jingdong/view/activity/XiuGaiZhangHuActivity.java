package bwie.com.jingdong.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import bwie.com.jingdong.R;

public class XiuGaiZhangHuActivity extends AppCompatActivity {

    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    private RelativeLayout xgyhm;
    private RelativeLayout xgnc;
    private TextView xnc;
    private SharedPreferences aa;
    private RelativeLayout xgtx;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView quxiao;
    private Dialog dialog;
    private ImageView xtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_zhang_hu);
        xtx = (ImageView) findViewById(R.id.xtx);
        xgyhm = (RelativeLayout) findViewById(R.id.xgyhm);
        xgnc = (RelativeLayout) findViewById(R.id.xgnc);
        xnc = (TextView) findViewById(R.id.xnc);
        xgtx = (RelativeLayout) findViewById(R.id.xgtx);
        aa = XiuGaiZhangHuActivity.this.getSharedPreferences("aa", Context.MODE_PRIVATE);
        //修改用户名
        xgyhm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XiuGaiZhangHuActivity.this, "用户名不支持修改呦~", Toast.LENGTH_SHORT).show();
            }
        });
        //修改昵称
        xgnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XiuGaiZhangHuActivity.this, NiChengXiuGaiActivity.class);
                startActivity(intent);
            }
        });
        xgtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(XiuGaiZhangHuActivity.this, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(XiuGaiZhangHuActivity.this).inflate(R.layout.dialog_layout, null);
                //初始化控件
                choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
                takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
                quxiao = (TextView) inflate.findViewById(R.id.quxiao);

                Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
                if(bt!=null){
                    @SuppressWarnings("deprecation")
                    Drawable drawable = new BitmapDrawable(bt);//转换成drawable
                }else{
                    /**
                     *  如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
                     *
                     */
                }
                //从相册选择
                choosePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(XiuGaiZhangHuActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
                //拍照
                takePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(XiuGaiZhangHuActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });
                quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //将布局设置给Dialog
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = aa.getString("username", null);
        String userimg = aa.getString("userimg", null);
        xnc.setText(name);
        xtx.setImageResource(Integer.parseInt(userimg));
    }

    //返回上一页
    public void fanhui(View view) {
        XiuGaiZhangHuActivity.this.finish();
    }


}
