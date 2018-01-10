package bwie.com.jingdong.view.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bwie.com.jingdong.R;
import bwie.com.jingdong.view.Liushi;


/**
 * Created by dell on 2017/12/12.
 */

public class SouSuo_1Fragment extends Fragment {

    private Liushi mFlowLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sousuo_1_layout, container, false);
        mFlowLayout= view.findViewById(R.id.liu);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //流式布局
        initChildViews();
    }
    private String mNames[] = {
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat",
            "shanshan","woaini1314","tiangchangdijiu"
            ,"玫瑰花","珊珊","ssddssdsdsdsdsdsdsadsadsadsdsad",
            "ssddssdsdsdsdsdsdsadsadsadsdsad"
    };
    private void initChildViews() {
        // TODO Auto-generated method stub

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 20;
        lp.rightMargin = 20;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for(int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(getActivity());
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            mFlowLayout.addView(view,lp);
        }
    }
}
