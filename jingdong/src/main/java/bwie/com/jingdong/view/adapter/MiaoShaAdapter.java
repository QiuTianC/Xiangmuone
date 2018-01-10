package bwie.com.jingdong.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bwie.com.jingdong.view.Holder.MiaoShaHolder;
import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.MiaoShaBean;
import bwie.com.jingdong.utils.imageLoader.ImageLoaderUtil;

/**
 * Created by dell on 2017/12/5.
 */

public class MiaoShaAdapter extends RecyclerView.Adapter<MiaoShaHolder>{
    private List<MiaoShaBean.MiaoshaBean.ListBeanX> list;
    private Context context;


    public MiaoShaAdapter(Context context, List<MiaoShaBean.MiaoshaBean.ListBeanX> list) {
        this.context = context;
        this.list = list;
    }


    //....回想listView的优化,,,1.创建viewHolder....2.与条目的视图进行绑定的
    //onCreateViewHolder...创建一个ViewHolder....根据条目的视图创建自己的holder对象
    @Override
    public MiaoShaHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //inflater...条目的视图
        View view = LayoutInflater.from(context).inflate(R.layout.layout_jdmiaosha, parent, false);


        MiaoShaHolder holder = new MiaoShaHolder(view);


        return holder;
    }

    /**
     * onBindViewHolder 绑定上viewHolder之后的操作...主要是赋值(textView,button,checkBox...)的操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MiaoShaHolder holder, int position) {


        holder.textView.setText(list.get(position).getTitle());
        String[] split = list.get(position).getImages().split("\\|");
        for ( int s=0;s<split.length;s++)
        {
            ImageLoader.getInstance().displayImage(split[s].toString(),holder.imageView, ImageLoaderUtil.getDefaultOption());
        }

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
