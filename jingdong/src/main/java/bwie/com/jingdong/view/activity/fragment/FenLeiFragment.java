package bwie.com.jingdong.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.Fen_LV_Bean;
import bwie.com.jingdong.utils.JsonCallBack;
import bwie.com.jingdong.utils.NetDataUtil;
import bwie.com.jingdong.view.activity.SouSuoActivity;
import bwie.com.jingdong.view.adapter.Fen_LV_Adapter;


/**
 * Created by dell on 2017/12/5.
 */

public class FenLeiFragment extends Fragment {
    private ListView listView;
    private FrameLayout frameLayout;
    private Fen_LV_Adapter fenLvAdapter;
    private ListView goodlv;
    private List<Fen_LV_Bean.DataBean> data;
    private RelativeLayout rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fenlei, container, false);
        listView = (ListView) view.findViewById(R.id.fen_lv);
        frameLayout = (FrameLayout) view.findViewById(R.id.fen_fram_layout);
        rl = view.findViewById(R.id.rl);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String path="https://www.zhaoapi.cn/product/getCatagory";
        NetDataUtil.getData( path,getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {
                Gson gson=new Gson();
                Fen_LV_Bean fen_lv_bean = gson.fromJson(json, Fen_LV_Bean.class);
                data = fen_lv_bean.getData();
                fenLvAdapter = new Fen_LV_Adapter(data,getActivity());
                listView.setAdapter(fenLvAdapter);
            }


        });
        FenLeiZiFragment newsFragment=new FenLeiZiFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fen_fram_layout,newsFragment).commit();
        Bundle bundle=new Bundle();
        bundle.putInt("cid",1);
        newsFragment.setArguments(bundle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fenLvAdapter.setmPosition(i);
                fenLvAdapter.notifyDataSetChanged();
                listView.smoothScrollToPositionFromTop(i,(adapterView.getHeight())-(view.getHeight())/2);

                int cid = data.get(i).getCid();

                FenLeiZiFragment newsFragment=new FenLeiZiFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.fen_fram_layout,newsFragment).commit();
                Bundle bundle=new Bundle();
                bundle.putInt("cid",cid);
                newsFragment.setArguments(bundle);

            }
        });

        //搜索跳转
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
            }
        });

    }
}
