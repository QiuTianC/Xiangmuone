package bwie.com.jingdong.view.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.MiaoShaBean;
import bwie.com.jingdong.utils.JsonCallBack;
import bwie.com.jingdong.utils.NetDataUtil;
import bwie.com.jingdong.view.activity.DengLuActivity;
import bwie.com.jingdong.view.activity.ZhangHuActivity;
import bwie.com.jingdong.view.adapter.TuiJianAdapter;

/**
 * Created by dell on 2017/12/5.
 */

public class WoDeFragment extends Fragment {

    private ILoadingLayout startLabels;
    private PullToRefreshScrollView refreshScrollView;
    private TuiJianAdapter tuiJianAdapter;
    private List<MiaoShaBean.TuijianBean.ListBean> tuilist=new ArrayList<>();
    private LinearLayout dneglu;
    private ImageView userimg;
    private TextView username;
    private boolean b;
    private SharedPreferences aa;
    private RecyclerView tuijian;
    private LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_wode, container, false);
        userimg = view.findViewById(R.id.userimg);
        username = view.findViewById(R.id.username);
        dneglu = view.findViewById(R.id.denglu);
        tuijian = view.findViewById(R.id.tuijian);
        ll = view.findViewById(R.id.tuill);
        refreshScrollView  = view.findViewById(R.id.refresh_scroll_view);
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        if(aa.getBoolean("is", false)){
            String img = aa.getString("userimg", null);
            String name = aa.getString("username", null);
            userimg.setImageResource(Integer.parseInt(img));
            username.setText(name);
            ll.setVisibility(View.VISIBLE);
        }else
        {
            userimg.setImageResource(R.drawable.user);
            username.setText("登录/注册");
            ll.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aa = getActivity().getSharedPreferences("aa", Context.MODE_PRIVATE);
        dneglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aa.getBoolean("is", false)){
                    Intent intent=new Intent(getActivity(), ZhangHuActivity.class);
                    startActivity(intent);
                }else
                {
                    Intent intent=new Intent(getActivity(), DengLuActivity.class);
                    startActivity(intent);
                }
            }
        });

        jdtuijian();
        tuijian.setFocusable(false);
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