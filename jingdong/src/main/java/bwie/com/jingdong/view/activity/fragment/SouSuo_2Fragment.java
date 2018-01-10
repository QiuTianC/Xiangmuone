package bwie.com.jingdong.view.activity.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.SouSuoBean;
import bwie.com.jingdong.utils.OkHttp3Util;
import bwie.com.jingdong.view.adapter.SimpleRecycleAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dell on 2017/12/12.
 */

public class SouSuo_2Fragment extends Fragment {

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sousuo_2_layout, container, false);
        rv = view.findViewById(R.id.rv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText text= getActivity().findViewById(R.id.name);
        String s = text.getText().toString();
        Map<String, String> params = new HashMap<>();

        params.put("keywords",s);
        params.put("page",1+"");
        Log.d("bbbbbbbbb",s+"      ");
        boolean networkAvailable = isNetworkAvailable(getActivity());
        if(networkAvailable)
        {
            OkHttp3Util.doPost("https://www.zhaoapi.cn/product/searchProducts", params, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String json = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            SouSuoBean souSuoBean = new Gson().fromJson(json, SouSuoBean.class);
                            List<SouSuoBean.DataBean> data = souSuoBean.getData();
                            Log.d("aaaaaaaaaaaa",data+"");
                            sss(data);

                        }
                    });

                }
            });
        }else {
            Toast.makeText(getActivity(),"网络不佳，请检查网络",Toast.LENGTH_SHORT).show();
        }

    }
    private void sss(List<SouSuoBean.DataBean> data) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        //设置适配器


        SimpleRecycleAdapter simpleRecycleAdapter = new SimpleRecycleAdapter(getActivity(),data);
        rv.setAdapter(simpleRecycleAdapter);
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
