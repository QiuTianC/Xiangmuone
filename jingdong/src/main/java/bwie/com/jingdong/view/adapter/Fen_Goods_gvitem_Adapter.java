package bwie.com.jingdong.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.DataFenGoodsBean;
import bwie.com.jingdong.utils.imageLoader.ImageLoaderUtil;

/**
 * Created by dell on 2017/12/7.
 */

public class Fen_Goods_gvitem_Adapter extends BaseAdapter{
    List<DataFenGoodsBean.DataBean.ListBean> data;
    Context con;

    public Fen_Goods_gvitem_Adapter(List<DataFenGoodsBean.DataBean.ListBean> data, Context con) {
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
        ViewHolder vh;
        if (view==null){
            view = View.inflate(con, R.layout.fen_gv_child, null);
            vh=new ViewHolder();
            vh.imageView=(ImageView) view.findViewById(R.id.fen_gv_img);
            vh.textView=view.findViewById(R.id.fen_gv_title);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.textView.setText(data.get(i).getName());
        ImageLoader.getInstance().displayImage(data.get(i).getIcon(),vh.imageView, ImageLoaderUtil.getDefaultOption());
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
