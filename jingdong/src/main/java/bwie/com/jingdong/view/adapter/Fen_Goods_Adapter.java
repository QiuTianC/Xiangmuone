package bwie.com.jingdong.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.DataFenGoodsBean;
import bwie.com.jingdong.view.MyGridView;


/**
 * Created by dell on 2017/12/7.
 */

public class Fen_Goods_Adapter extends BaseAdapter{
    List<DataFenGoodsBean.DataBean> data;
    Context con;

    public Fen_Goods_Adapter(List<DataFenGoodsBean.DataBean> data, Context con) {
        this.data = data;
        this.con = con;
    }

    @Override
    public int getCount() {
        if (data!=null){
            return data.size();
        }else {
            return 10;
        }
    }

    @Override
    public Object getItem(int i) {
        return data.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        List<DataFenGoodsBean.DataBean.ListBean> list = data.get(i).getList();

        ViewHolder vh;
        if (view==null){
            view = View.inflate(con, R.layout.fen_goods_lv_child, null);
            vh=new ViewHolder();
            vh.myGridView=(MyGridView) view.findViewById(R.id.fen_mgv);
            vh.textView=view.findViewById(R.id.fen_goods_txt);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.textView.setText(data.get(i).getName());
        Fen_Goods_gvitem_Adapter gvitemAdapter=new Fen_Goods_gvitem_Adapter(list,con);
        vh.myGridView.setAdapter(gvitemAdapter);

        return view;
    }
    class ViewHolder{
        TextView textView;
        MyGridView myGridView;
    }
}
