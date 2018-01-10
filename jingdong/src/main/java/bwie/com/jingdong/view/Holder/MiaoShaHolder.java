package bwie.com.jingdong.view.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bwie.com.jingdong.R;

/**
 * Created by dell on 2017/12/5.
 */

public class MiaoShaHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;

    public MiaoShaHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.img);
    }
}
