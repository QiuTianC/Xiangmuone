package bwie.com.jingdong.view.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bwie.com.jingdong.R;


/**
 * Created by dell on 2017/12/9.
 */

public class SimpleRecycleHorder extends RecyclerView.ViewHolder {
    public TextView title;
    public   ImageView img;
    public  TextView price;


    public SimpleRecycleHorder(View itemView) {
        super(itemView);


        title = itemView.findViewById(R.id.title);
        img = itemView.findViewById(R.id.img);
        price = itemView.findViewById(R.id.price);
    }
}
