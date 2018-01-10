package bwie.com.jingdong.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bwie.com.jingdong.view.Holder.SimpleRecycleHorder;
import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.SouSuoBean;
import bwie.com.jingdong.utils.imageLoader.ImageLoaderUtil;

/**
 * Created by dell on 2017/12/9.
 */

public class SimpleRecycleAdapter extends RecyclerView.Adapter<SimpleRecycleHorder> {
    private List<SouSuoBean.DataBean> list;
    private Context context;


    public SimpleRecycleAdapter(Context context, List<SouSuoBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    //....回想listView的优化,,,1.创建viewHolder....2.与条目的视图进行绑定的
    //onCreateViewHolder...创建一个ViewHolder....根据条目的视图创建自己的holder对象
    @Override
    public SimpleRecycleHorder onCreateViewHolder(ViewGroup parent, int viewType) {


        //inflater...条目的视图
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);


        SimpleRecycleHorder holder = new SimpleRecycleHorder(view);


        return holder;
    }


    /**
     * onBindViewHolder 绑定上viewHolder之后的操作...主要是赋值(textView,button,checkBox...)的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(SimpleRecycleHorder holder, int position) {

        ImageLoader.getInstance().displayImage(list.get(position).getImages(),holder.img, ImageLoaderUtil.getDefaultOption());
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText(list.get(position).getPrice()+"");

    }


    /**
     * 条目的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}
