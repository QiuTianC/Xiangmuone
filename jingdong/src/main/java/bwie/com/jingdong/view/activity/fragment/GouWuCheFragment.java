package bwie.com.jingdong.view.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.CartBean;
import bwie.com.jingdong.model.bean.CountPriceBean;
import bwie.com.jingdong.model.bean.MiaoShaBean;
import bwie.com.jingdong.presenter.CartPresenter;
import bwie.com.jingdong.utils.CommonUtils;
import bwie.com.jingdong.utils.JsonCallBack;
import bwie.com.jingdong.utils.NetDataUtil;
import bwie.com.jingdong.view.CartExpanableListview;
import bwie.com.jingdong.view.IView.IMainActivity;
import bwie.com.jingdong.view.activity.DengLuActivity;
import bwie.com.jingdong.view.activity.QueRenActivity;
import bwie.com.jingdong.view.adapter.MyAdapter;
import bwie.com.jingdong.view.adapter.TuiJianAdapter;


/**
 * Created by dell on 2017/12/5.
 */

public class GouWuCheFragment extends Fragment implements IMainActivity,View.OnClickListener{
    private List<MiaoShaBean.TuijianBean.ListBean> tuilist=new ArrayList<>();
    private CartExpanableListview expanableListview;
    private String cartUrl = "https://www.zhaoapi.cn/product/getCarts?uid=4084";
    private CartPresenter cartPresenter;
    private Gson gson;
    private RecyclerView tuijian;
    private MyAdapter myAdapter;
    private CheckBox check_all;
    private TuiJianAdapter tuiJianAdapter;
    private List<List<CartBean.DataBean.ListBean>> listChilds;
    private List<CartBean.DataBean> listGroup;
    private TextView text_total;
    private ILoadingLayout startLabels;
    private TextView text_buy;
    private SharedPreferences aa;
    private PullToRefreshScrollView refreshScrollView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                CountPriceBean countPriceBean = (CountPriceBean) msg.obj;

                //设置
                text_total.setText("合计:¥"+countPriceBean.getPrice());
                text_buy.setText("去结算("+countPriceBean.getCount()+")");
            }else  if (msg.what == 1){//改变全选
                boolean flag = (boolean) msg.obj;

                check_all.setChecked(flag);
            }
        }
    };
    private LinearLayout dlll;
    private Button goudenglu;
    private LinearLayout linear_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_gouwuche, container, false);
        refreshScrollView  = view.findViewById(R.id.refresh_scroll_view);
        linear_layout = view.findViewById(R.id.linear_layout);
        goudenglu = view.findViewById(R.id.goudenglu);
        check_all = view.findViewById(R.id.check_all);
        text_total = view.findViewById(R.id.text_total);
        text_buy = view.findViewById(R.id.text_buy);
        dlll = view.findViewById(R.id.dlll);
        tuijian = view.findViewById(R.id.tuijian);

        expanableListview = view.findViewById(R.id.expanable_listview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       //去掉默认的指示器
        expanableListview.setGroupIndicator(null);

        cartPresenter = new CartPresenter(this);
        gson = new Gson();

        //全选:...点击事件
        check_all.setOnClickListener(this);
        text_buy.setOnClickListener(this);
        aa = getActivity().getSharedPreferences("aa", Context.MODE_PRIVATE);

        //登录点击事件
        goudenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DengLuActivity.class);
                startActivity(intent);
            }
        });
        tuijian.setFocusable(false);
        jdtuijian();
        //2.设置刷新模式
        /*设置pullToRefreshListView的刷新模式，BOTH代表支持上拉和下拉，PULL_FROM_END代表上拉,PULL_FROM_START代表下拉 */
        refreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);


        //3.通过getLoadingLayoutProxy 方法来指定上拉和下拉时显示的状态的区别(也就是设置向下拉的时候头部里面显示的文字)
        //此时这里设置的是下拉刷新的时候显示的文字,所以第一个设置true表示现在是刷新,第二个设置为false
        startLabels = refreshScrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");
        startLabels.setRefreshingLabel("正在刷新...");
        startLabels.setReleaseLabel("放开刷新");


        ILoadingLayout endLabels = refreshScrollView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(aa.getBoolean("is", false)){
            //请求数据
            cartPresenter.getCartData(cartUrl);
            expanableListview.setVisibility(View.VISIBLE);
            linear_layout.setVisibility(View.VISIBLE);
            dlll.setVisibility(View.INVISIBLE);
        }
        else
        {
            expanableListview.setVisibility(View.INVISIBLE);
            linear_layout.setVisibility(View.INVISIBLE);
            dlll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSuccessCartData(final String json) {
        CommonUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if(json == null || "null".equals(json) || json==""){
                    Toast.makeText(getActivity(),"购物车空,去逛逛",Toast.LENGTH_SHORT).show();
                }else{
                    CartBean cartBean = new Gson().fromJson(json, CartBean.class);
                    listGroup = cartBean.getData();
                    listChilds = new ArrayList<>();
                    for (int i = 0; i< listGroup.size(); i++)
                    {
                        listChilds.add(listGroup.get(i).getList());
                    }
                    for(int i = 0; i< listGroup.size(); i++)
                    {
                        if(isAllChildInGroupSelected(i)){
                            listGroup.get(i).setGroupChecked(true);
                        }
                    }
                    check_all.setChecked(isAllGroupChecked());

                    sendPriceAndCount();
                    myAdapter = new MyAdapter(getActivity(), listGroup, listChilds,handler);
                    expanableListview.setAdapter(myAdapter);
                    for (int i = 0; i< listGroup.size(); i++)
                    {
                        expanableListview.expandGroup(i);
                    }
                }


            }



        });
    }
    private void sendPriceAndCount() {
        double price = 0;
        int count = 0;

        //通过判断二级列表是否勾选,,,,计算价格数量
        for (int i=0;i<listGroup.size();i++){
            for (int j = 0;j<listChilds.get(i).size();j++){
                if (listChilds.get(i).get(j).getSelected() == 1){
                    price += listChilds.get(i).get(j).getNum() * listChilds.get(i).get(j).getPrice();
                    count += listChilds.get(i).get(j).getNum();
                }
            }
        }

        CountPriceBean countPriceBean = new CountPriceBean(price, count);
        //传给activity进行显示
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = countPriceBean;
        handler.sendMessage(msg);
    }
    private boolean isAllGroupChecked(){
        for(int i=0;i<listGroup.size();i++)
        {
            if(!listGroup.get(i).isGroupChecked()){
                return false;
            }
        }
        return true;
    }
    private boolean isAllChildInGroupSelected(int groupPosition){
        for (int i=0;i<listChilds.get(groupPosition).size();i++)
        {
            if(listChilds.get(groupPosition).get(i).getSelected()==0)
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_all://1.点击全选:选中/未选中...调用适配器中的方法...myAdapter.setIsCheckAll(true);来设置所有的一级和二级是否选中,计算

                myAdapter.setIfCheckAll(check_all.isChecked());//适配器中的所有checkBox是否选中

                break;
            case R.id.text_buy:

                double price = 0;
                int count = 0;

                //通过判断二级列表是否勾选,,,,计算价格数量
                for (int i=0;i<listGroup.size();i++){
                    for (int j = 0;j<listChilds.get(i).size();j++){
                        if (listChilds.get(i).get(j).getSelected() == 1){
                            price += listChilds.get(i).get(j).getNum() * listChilds.get(i).get(j).getPrice();
                            count += listChilds.get(i).get(j).getNum();
                        }
                    }
                }

                CountPriceBean countPriceBean = new CountPriceBean(price, count);
                double price1 = countPriceBean.getPrice();
                Intent intent = new Intent(getActivity(), QueRenActivity.class);

                intent.putExtra("order",countPriceBean);

                startActivity(intent);



                break;
        }
    }
    private void jdtuijian() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData("https://www.zhaoapi.cn/ad/getAd",getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {

                //解析
                Gson gson = new Gson();
                MiaoShaBean fenbean = gson.fromJson(json, MiaoShaBean.class);
                //往后面添加...
                tuilist.addAll(fenbean.getTuijian().getList());
                tuijian.setLayoutManager(new GridLayoutManager(getActivity(),2, OrientationHelper.VERTICAL,false));
                //设置适配器
                tuijianadapter();

            }
        });
    }
    private void tuijianadapter() {
        if (tuiJianAdapter== null){
            tuiJianAdapter = new TuiJianAdapter(getActivity(),tuilist);
            tuijian.setAdapter(tuiJianAdapter);
        }else {
            tuiJianAdapter.notifyDataSetChanged();
        }
    }
}
