package bwie.com.jingdong.view.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bwie.com.jingdong.R;
import bwie.com.jingdong.model.bean.Fenbean;
import bwie.com.jingdong.model.bean.LunBoBean;
import bwie.com.jingdong.model.bean.MiaoShaBean;
import bwie.com.jingdong.utils.JsonCallBack;
import bwie.com.jingdong.utils.NetDataUtil;
import bwie.com.jingdong.view.activity.SouSuoActivity;
import bwie.com.jingdong.view.adapter.JdFenleiadpter;
import bwie.com.jingdong.view.adapter.LunBoAdapter;
import bwie.com.jingdong.view.adapter.MiaoShaAdapter;
import bwie.com.jingdong.view.adapter.TuiJianAdapter;

/**
 * Created by dell on 2017/12/5.
 */

public class ShouYeFragment extends Fragment {

    private TextView miaosha_time;
    private TextView miaosha_shi;
    private TextView miaosha_minter;
    private TextView miaosha_second;
    private PullToRefreshScrollView refreshScrollView;
    private ViewPager viewPager;
    private ListView listView;
    //private List<DataDataBean.ResultsBean> list = new ArrayList<>();//记录当前展示的所有数据
    private int i = 0;
    private int page_num = 1;
    private TuiJianAdapter tuiJianAdapter;
    private MiaoShaAdapter miaoShaAdapter;
    private JdFenleiadpter jdFenleiadpter;
    private ILoadingLayout startLabels;
    private List<String> imageUrlList;
    private List<Fenbean.DataBean> fenlist = new ArrayList<>();
    private List<MiaoShaBean.MiaoshaBean.ListBeanX> miaolist = new ArrayList<>();
    private List<MiaoShaBean.TuijianBean.ListBean> tuilist = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                //显示下一页....拿到当前页+1
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                //再次发送消息
                handler.sendEmptyMessageDelayed(0, 2000);
            }
            if (msg.what == 1) {
                podata.setText(paolist.get(msg.arg1 % 4));
            }
        }
    };
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTime();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private RecyclerView jdfenlei;
    private RecyclerView miaosha;
    private RecyclerView tuijian;
    private TextView podata;
    private List<String> paolist;
    private View view;
    private RelativeLayout rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_shouye, container, false);
        refreshScrollView = view.findViewById(R.id.refresh_scroll_view);
        miaosha = view.findViewById(R.id.miaosha);
        viewPager = view.findViewById(R.id.image_view_pager);
        jdfenlei = view.findViewById(R.id.jdfenlei);
        tuijian = view.findViewById(R.id.tuijian);
        podata = view.findViewById(R.id.paodata);
        rl = view.findViewById(R.id.rl);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //搜索跳转
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
            }
        });
        initView();
        handler2.sendEmptyMessage(0);
        miaosha.setFocusable(false);
        jdfenlei.setFocusable(false);
        tuijian.setFocusable(false);
        //轮播图
        lunBoTu();
        jdfenlei();
        jdmiaosha();
        jdtuijian();
        paomadeng();
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


    private void paomadeng() {
        paolist = new ArrayList<>();
        paolist.add("婉转十大撒旦发鬼地方个");
        paolist.add("的方式发送给发个大幅度");
        paolist.add("发郭德纲地方郭德纲国东方");
        paolist.add("发个啥的打法范德费过");
        paolist.add("点睡感到颂德歌功");
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    i++;
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.arg1 = i;
                    handler.sendMessage(obtain);
                }

            }
        }.start();
    }

    public void initView() {
        miaosha_time = (TextView) view.findViewById(R.id.tv_miaosha_time);
        miaosha_shi = (TextView) view.findViewById(R.id.tv_miaosha_shi);
        miaosha_minter = (TextView) view.findViewById(R.id.tv_miaosha_minter);
        miaosha_second = (TextView) view.findViewById(R.id.tv_miaosha_second);
    }

    //秒杀倒计时
    public void setTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String format = df.format(curDate);
        StringBuffer buffer = new StringBuffer();
        String substring = format.substring(0, 11);
        buffer.append(substring);
        Log.d("ccc", substring);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour % 2 == 0) {
            miaosha_time.setText(hour + "点场");
            buffer.append((hour + 2));
            buffer.append(":00:00");
        } else {
            miaosha_time.setText((hour - 1) + "点场");
            buffer.append((hour + 1));
            buffer.append(":00:00");
        }
        String totime = buffer.toString();
        try {
            Date date = df.parse(totime);
            Date date1 = df.parse(format);
            long defferenttime = date.getTime() - date1.getTime();
            long days = defferenttime / (1000 * 60 * 60 * 24);
            long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = defferenttime % 60000;
            long second = Math.round((float) seconds / 1000);
            miaosha_shi.setText("0" + hours + "");
            if (minute >= 10) {
                miaosha_minter.setText(minute + "");
            } else {
                miaosha_minter.setText("0" + minute + "");
            }
            if (second >= 10) {
                miaosha_second.setText(second + "");
            } else {
                miaosha_second.setText("0" + second + "");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void jdfenlei() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData("https://www.zhaoapi.cn/product/getCatagory", getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {
                //解析
                Gson gson = new Gson();
                if(gson.toString().length()>0)
                {
                Fenbean fenbean = gson.fromJson(json, Fenbean.class);
                    if("0".equals(fenbean.getCode()))
                    {
                        //往后面添加...
                        fenlist.addAll(fenbean.getData());
                        jdfenlei.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.HORIZONTAL, false));
                        //设置适配器
                        jdfenleisetAdapter();


                        //停止刷新
                        refreshScrollView.onRefreshComplete();
                    }
                    else
                    {
                        jdfenlei();
                    }
                }else {
                    jdfenlei();
                }
            }
        });

    }

    private void jdtuijian() {
        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData("https://www.zhaoapi.cn/ad/getAd", getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {

                //解析
                Gson gson = new Gson();
                if(gson.toString().length()>0){
                    MiaoShaBean fenbean = gson.fromJson(json, MiaoShaBean.class);
                    //往后面添加...
                    if("0".equals(fenbean.getCode()))
                    {
                        tuilist.addAll(fenbean.getTuijian().getList());
                        tuijian.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false));
                        //设置适配器
                        tuijianadapter();


                        //停止刷新
                        refreshScrollView.onRefreshComplete();
                    }else {
                        jdtuijian();
                    }

                }else {
                    jdtuijian();
                }

            }
        });
    }

    private void jdmiaosha() {

        //第一个参数是接口,第二个上下文,第三个回调json数据
        NetDataUtil.getData("https://www.zhaoapi.cn/ad/getAd", getActivity(), new JsonCallBack() {


            @Override
            public void getJsonString(String json) {


                //解析
                Gson gson = new Gson();
                if(gson.toString().length()>0)
                {
                    MiaoShaBean fenbean = gson.fromJson(json, MiaoShaBean.class);
                    if("0".equals(fenbean.getCode())){
                        //往后面添加...
                        miaolist.addAll(fenbean.getMiaosha().getList());
                        miaosha.setLayoutManager(new GridLayoutManager(getActivity(), 1, OrientationHelper.HORIZONTAL, false));
                        //设置适配器
                        miaoshaadpter();


                        //停止刷新
                        refreshScrollView.onRefreshComplete();
                    }else
                    {
                        jdmiaosha();
                    }

                }else
                {
                    jdmiaosha();
                }

            }
        });

    }

    /**
     * 轮播图的方法
     */
    private void lunBoTu() {

        NetDataUtil.getData("https://www.zhaoapi.cn/ad/getAd", getActivity(), new JsonCallBack() {
            @Override
            public void getJsonString(String json) {
                //这个结合记录轮播图的所有地址
                imageUrlList = new ArrayList<String>();
                //解析数据/c
                Gson gson = new Gson();
                if (gson.toString().toString().length()>0) {
                    LunBoBean dataDataBean = gson.fromJson(json, LunBoBean.class);
                    if("0".equals(dataDataBean.getCode())){
                        List<LunBoBean.DataBean> essay = dataDataBean.getData();
                        for (LunBoBean.DataBean essayBean : essay) {
                            //essayBean.getAuthor().get(0).getWeb_url()

                            imageUrlList.add(essayBean.getIcon());
                        }
                        //此时应该根据图片的路径,加载图片,设置适配器
                        LunBoAdapter viewPagerAdapter = new LunBoAdapter(getActivity(), imageUrlList);
                        viewPager.setAdapter(viewPagerAdapter);
                        //1.手动可以无限滑动....maxValue....把当前开始展示的位置放在足够大的某个位置
                        viewPager.setCurrentItem(imageUrlList.size() * 100000);
                        //2.自动轮播
                        handler.sendEmptyMessageDelayed(0, 2000);
                    }else
                    {
                        lunBoTu();
                    }

                } else {
                    lunBoTu();
                    Toast.makeText(getActivity(), "网络不佳请耐心等待。。。", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void jdfenleisetAdapter() {
        if (jdFenleiadpter == null) {
            jdFenleiadpter = new JdFenleiadpter(getActivity(), fenlist);
            jdfenlei.setAdapter(jdFenleiadpter);
        } else {
            jdFenleiadpter.notifyDataSetChanged();
        }
    }

    private void miaoshaadpter() {
        if (miaoShaAdapter == null) {
            miaoShaAdapter = new MiaoShaAdapter(getActivity(), miaolist);
            miaosha.setAdapter(miaoShaAdapter);
        } else {
            miaoShaAdapter.notifyDataSetChanged();
        }
    }

    private void tuijianadapter() {
        if (tuiJianAdapter == null) {
            tuiJianAdapter = new TuiJianAdapter(getActivity(), tuilist);
            tuijian.setAdapter(tuiJianAdapter);
        } else {
            tuiJianAdapter.notifyDataSetChanged();
        }
    }

}
