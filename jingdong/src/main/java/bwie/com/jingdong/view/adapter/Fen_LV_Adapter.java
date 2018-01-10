package bwie.com.jingdong.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.Fen_LV_Bean;

/**
 * Created by dell on 2017/12/7.
 */

public class Fen_LV_Adapter extends BaseAdapter {
    List<Fen_LV_Bean.DataBean> data;
    Context con;
    private TextView textView;
    private  int mPosition;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public int getmPosition() {
        return mPosition;
    }

    public Fen_LV_Adapter(List<Fen_LV_Bean.DataBean> data, Context con) {
        this.data = data;
        this.con = con;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(con, R.layout.fen_lv_child, null);
        textView = (TextView) view1.findViewById(R.id.lv_txt);

        textView.setText(data.get(i).getName());
        if (i == mPosition) {
            view1.setBackgroundColor(Color.TRANSPARENT);
            textView.setTextColor(Color.RED);
        }else{
            textView.setTextColor(Color.BLACK);
            view1.setBackgroundColor(Color.WHITE);
        }


        return view1;
    }
}
