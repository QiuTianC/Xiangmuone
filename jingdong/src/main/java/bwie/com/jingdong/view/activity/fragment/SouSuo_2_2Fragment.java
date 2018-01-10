package bwie.com.jingdong.view.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import bwie.com.jingdong.R;

/**
 * Created by dell on 2017/12/12.
 */

public class SouSuo_2_2Fragment extends Fragment {
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sousuo_2_2layout, container, false);
        lv = view.findViewById(R.id.lv);
        return view;

    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String name = getArguments().getString("name", "");
        String text = getArguments().getString("text", "");
        if (name.equals("默认"))
        {
            name="0";
        }else if(name.equals("销量"))
        {
            name="1";
        }
        else if(name.equals("价格"))
        {
            name="1";
        }
        getDataFromNet(name,text);
    }
    private void getDataFromNet(String name,  String text) {
        Map<String, String> params = new HashMap<>();

        params.put("keywords",text);
        params.put("page",1+"");
        params.put("sort",name);
        Log.d("bbbbbbbbb",text+"       "+name);
        OkHttp3Util.doPost("https://www.zhaoapi.cn/product/searchProducts", params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                SouSuoBean souSuoBean = new Gson().fromJson(json, SouSuoBean.class);
                List<SouSuoBean.DataBean> data = souSuoBean.getData();
                Log.d("aaaaaaaaaaaa",data+"");

                sss(data);
            }
        });
    }
    private void sss(List<SouSuoBean.DataBean> data) {

        //设置适配器
        SimpleRecycleAdapter simpleRecycleAdapter = new SimpleRecycleAdapter(getActivity(),data);
        lv.setAdapter(simpleRecycleAdapter);
    }*/

}
