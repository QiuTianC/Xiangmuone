package bwie.com.jingdong.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.CartBean;
import bwie.com.jingdong.model.bean.CountPriceBean;
import bwie.com.jingdong.utils.CommonUtils;
import bwie.com.jingdong.utils.OkHttp3Util;
import bwie.com.jingdong.utils.imageLoader.ImageLoaderUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static bwie.com.jingdong.utils.OkHttp3Util.doPost;

/**
 * Created by Dash on 2017/12/12.
 */
public class MyAdapter extends BaseExpandableListAdapter{
    private  Handler handler;
    private  List<List<CartBean.DataBean.ListBean>> listChilds;
    private  List<CartBean.DataBean> listGroup;
    private Context context;

    public MyAdapter(Context context, List<CartBean.DataBean> listGroup, List<List<CartBean.DataBean.ListBean>> listChilds, Handler handler) {
        this.context=context;
        this.listGroup=listGroup;
        this.listChilds=listChilds;
        this.handler=handler;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChilds.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if(convertView==null)
        {
            convertView= View.inflate(context, R.layout.group_item_layout,null);
            groupHolder=new GroupHolder();
            groupHolder.check_group= convertView.findViewById(R.id.check_group);
            groupHolder.text_group = convertView.findViewById(R.id.text_group);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final CartBean.DataBean dataBean = listGroup.get(groupPosition);
        groupHolder.check_group.setChecked(dataBean.isGroupChecked());
        groupHolder.text_group.setText(dataBean.getSellerName());

        groupHolder.check_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setGroupChecked(!dataBean.isGroupChecked());
                changeChildState(groupPosition,dataBean.isGroupChecked());
                changeAllState(isAllGroupChecked());

                sendPriceAndCount();;
                notifyDataSetChanged();
            }


        });
        return convertView;
    }
    private void changeAllState(boolean allGroupChecked) {
        Message msg=Message.obtain();
        msg.what=1;
        msg.obj=allGroupChecked;
        handler.sendMessage(msg);
    }
    private boolean isAllGroupChecked(){
        for (int i=0;i<listGroup.size();i++)
        {
            if (!listGroup.get(i).isGroupChecked()){
                return false;
            }
        }
        return true;
    }
    private void changeChildState(int groupPosition, boolean groupChecked){
        List<CartBean.DataBean.ListBean> listBeen = listChilds.get(groupPosition);
        for (int i=0;i<listBeen.size();i++)
        {
            final CartBean.DataBean.ListBean listBean = listChilds.get(groupPosition).get(i);


            //更改网络孩子状态
            //跟新购物车的状态....服务器上选中和未选中的状态
            Map<String, String> params = new HashMap<>();
            params.put("uid","4084");
            params.put("sellerid", String.valueOf(listChilds.get(groupPosition).get(i).getSellerid()));
            params.put("pid", String.valueOf(listBean.getPid()));
            params.put("selected", String.valueOf(groupChecked? 1:0));
            params.put("num", String.valueOf(listBean.getNum()));
            doPost("https://www.zhaoapi.cn/product/updateCarts", params, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });

            //改变当前组中所有孩子的状态
            listBean.setSelected(groupChecked? 1:0);


        }
    };
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView==null)
        {
            convertView= View.inflate(context,R.layout.child_item_layout,null);
            childHolder=new ChildHolder();
            childHolder.text_add=convertView.findViewById(R.id.text_add);
            childHolder.text_num= convertView.findViewById(R.id.text_num);
            childHolder.text_jian= convertView.findViewById(R.id.text_jian);
            childHolder.text_title= convertView.findViewById(R.id.text_title);
            childHolder.text_price= convertView.findViewById(R.id.text_price);
            childHolder.image_good= convertView.findViewById(R.id.image_good);
            childHolder.check_child= convertView.findViewById(R.id.check_child);
            convertView.setTag(childHolder);
        }else
        {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final CartBean.DataBean.ListBean listBean = listChilds.get(groupPosition).get(childPosition);
        childHolder.text_num.setText(listBean.getNum()+"");
        childHolder.text_price.setText("￥"+listBean.getPrice());
        childHolder.text_title.setText(listBean.getTitle());
        childHolder.check_child.setChecked(listBean.getSelected()==0? false:true);

        ImageLoader.getInstance().displayImage(listBean.getImages().split("\\|")[0],childHolder.image_good, ImageLoaderUtil.getRoundOption());
        childHolder.check_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跟新购物车的状态....服务器上选中和未选中的状态
                Map<String, String> params = new HashMap<>();
                params.put("uid","4084");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected() == 1?0:1));
                params.put("num", String.valueOf(listBean.getNum()));
                doPost("https://www.zhaoapi.cn/product/updateCarts", params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CommonUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                // 3.1点击改变当前子条目状态:...实际上改的是当前位置的数据...改成与之前相反的1-->0 0--->1
                                listBean.setSelected(listBean.getSelected() == 1?0:1);
                                //3.2发送价钱和数量给界面显示....sendPriceAndCount();
                                sendPriceAndCount();

                                //当前子条目是否选中
                                if (listBean.getSelected() == 1){
                                    //判断一下当前组中所有的子条目是否全部选中:...isAllChildSelected(groupPosition)
                                    if (isAllChildInGroupSelected(groupPosition)){
                                        //改变一下当前组的状态:...changGroupState(groupPosition,true)
                                        changeGroupState(groupPosition,true);

                                        // ;...确定是否改变全选changeAllState(isAllGroupChecked());
                                        changeAllState(isAllGroupChecked());
                                    }

                                }else {
                                    //changGroupState(groupPosition,false);改变当前组false...是否全选changeAllState(isAllGroupChecked());
                                    changeGroupState(groupPosition,false);

                                    //全选是否选中
                                    changeAllState(isAllGroupChecked());

                                }

                                //刷新适配器
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
        childHolder.text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前num++
                final int num = listBean.getNum();

                //更改网络数量
                Map<String, String> params = new HashMap<>();
                params.put("uid","4084");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                params.put("num", String.valueOf(num+1));
                doPost("https://www.zhaoapi.cn/product/updateCarts", params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CommonUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                listBean.setNum(num +1);

                                if (listBean.getSelected() == 1){//当前位置选中
                                    sendPriceAndCount();
                                }

                                //刷新适配器
                                notifyDataSetChanged();
                            }
                        });
                    }
                });


            }
        });
        childHolder.text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int num = listBean.getNum();
                if (num == 1) {
                    return;
                }
                //更改网络数量
                Map<String, String> params = new HashMap<>();
                params.put("uid","4084");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                params.put("num", String.valueOf(num-1));
                OkHttp3Util.doPost("https://www.zhaoapi.cn/product/updateCarts", params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CommonUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                //改变数据
                                listBean.setNum(num -1);

                                if (listBean.getSelected() == 1){//当前位置选中
                                    sendPriceAndCount();
                                }

                                //刷新适配器
                                notifyDataSetChanged();
                            }
                        });
                    }
                });


            }
        });

        return convertView;
    }

    private void changeGroupState(int groupPosition, boolean b)
    {
        listGroup.get(groupPosition).setGroupChecked(b);
    }
    private boolean isAllChildSelected(int groupPosition){
        List<CartBean.DataBean.ListBean> listBeen = listChilds.get(groupPosition);
        for (int i=0;i<listBeen.size();i++)
        {
            if(listBeen.get(i).getSelected()==0)
            {
                return false;
            }
        }
        return true;
    };
    private boolean isAllChildInGroupSelected(int groupPosition) {
        for (int i= 0;i<listChilds.get(groupPosition).size();i++){
            //只要有一个没选中就返回false
            if (listChilds.get(groupPosition).get(i).getSelected() ==0){
                return false;
            }
        }

        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public  void setIfCheckAll(boolean checked)
    {
        for (int i=0;i<listGroup.size();i++) {
            //通过一个for循环 设置一级是否选中
            listGroup.get(i).setGroupChecked(checked);
            for (int j = 0; j < listChilds.get(i).size(); j++) {

                CartBean.DataBean.ListBean listBean = listChilds.get(i).get(j);
                //更改网络孩子状态
                //跟新购物车的状态....服务器上选中和未选中的状态
                Map<String, String> params = new HashMap<>();
                params.put("uid", "4084");
                params.put("sellerid", String.valueOf(listChilds.get(i).get(j).getSellerid()));
                params.put("pid", String.valueOf(listChilds.get(i).get(j).getPid()));
                params.put("selected", String.valueOf(checked ? 1 : 0));
                params.put("num", String.valueOf(listChilds.get(i).get(j).getNum()));
                OkHttp3Util.doPost("https://www.zhaoapi.cn/product/updateCarts", params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });

                //改变选中的状态
                listBean.setSelected(checked ? 1 : 0);
            }
        }

        //刷新适配器
        notifyDataSetChanged();

        //计算价格和数量
        sendPriceAndCount();
    }

    private void sendPriceAndCount() {
        double price=0;
        int count=0;
        for (int i=0;i<listGroup.size();i++)
        {
            List<CartBean.DataBean.ListBean> list = listGroup.get(i).getList();
            for (int j=0;j<list.size();j++)
            {
                CartBean.DataBean.ListBean listBean = list.get(j);
                if(listBean.getSelected()==1)
                {
                    price+=listBean.getPrice()*listBean.getNum();
                    count+=listBean.getNum();
                }
            }
        }

        CountPriceBean countPriceBean=new CountPriceBean(price,count);
        Message msg=Message.obtain();
        msg.what=0;
        msg.obj=countPriceBean;
        handler.sendMessage(msg);
    }



    private class GroupHolder{
        CheckBox check_group;
        TextView text_group;
    }
    private class ChildHolder{
        CheckBox check_child;
        ImageView image_good;
        TextView text_title;
        TextView text_price;
        TextView text_jian;
        TextView text_num;
        TextView text_add;
    }
}
