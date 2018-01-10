package bwie.com.jingdong.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.DataFenGoodsBean;
import bwie.com.jingdong.utils.JsonCallBack;
import bwie.com.jingdong.utils.NetDataUtil;
import bwie.com.jingdong.view.adapter.Fen_Goods_Adapter;

/**
 * Created by dell on 2017/12/6.
 */

public class FenLeiZiFragment extends Fragment {
    private ListView goodslv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fen_frag_news, container, false);
        goodslv = (ListView) view.findViewById(R.id.fen_good_lv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = getArguments().getString("name", "");
        int cid = getArguments().getInt("cid", 0);

        String path2="https://www.zhaoapi.cn/product/getProductCatagory?cid="+cid;
        NetDataUtil.getData(path2,getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {
                Gson gson=new Gson();
                DataFenGoodsBean dataFenGoodsBean = gson.fromJson(json, DataFenGoodsBean.class);
                List<DataFenGoodsBean.DataBean> data2 = dataFenGoodsBean.getData();
                Fen_Goods_Adapter goodsAdapter=new Fen_Goods_Adapter(data2,getActivity());
                goodslv.setAdapter(goodsAdapter);
            }


        });
    }


}
