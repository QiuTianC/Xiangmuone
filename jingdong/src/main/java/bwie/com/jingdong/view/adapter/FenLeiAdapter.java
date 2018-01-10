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
import bwie.com.jingdong.model.bean.FenLeiBean;
import bwie.com.jingdong.utils.imageLoader.ImageLoaderUtil;

/**
 * Created by dell on 2017/12/6.
 */

public class FenLeiAdapter extends BaseAdapter {
    int you=0;
    int wu=1;
    Context context;
    private List<FenLeiBean.DataBean> list;

    public FenLeiAdapter(Context context, List<FenLeiBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getImg()!=null)
        {
            return you;
        }
        return wu;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i) == wu){
            TextHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_text_layout,null);
                holder = new TextHolder();

                holder.textView = view.findViewById(R.id.text_title);

                view.setTag(holder);


            }else {
                holder = (TextHolder) view.getTag();
            }

            holder.textView.setText(list.get(i).getTitle());

        }else if (getItemViewType(i) == you){

            ImageHolder holder;
            if (view == null){
                view = View.inflate(context, R.layout.item_image_layout,null);
                holder = new ImageHolder();

                holder.textView = view.findViewById(R.id.text_title);
                holder.imageView = view.findViewById(R.id.image_view);

                view.setTag(holder);


            }else {
                holder = (ImageHolder) view.getTag();
            }

            holder.textView.setText(list.get(i).getTitle());
            //设置图片的显示
            ImageLoader.getInstance().displayImage(list.get(i).getImg(),holder.imageView, ImageLoaderUtil.getRoundOption());


        }

        return view;
    }

    private class TextHolder{
        TextView textView;
    }
    private class ImageHolder{
        TextView textView;
        ImageView imageView;
    }
}
