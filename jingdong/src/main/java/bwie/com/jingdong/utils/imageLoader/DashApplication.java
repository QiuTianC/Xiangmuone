package bwie.com.jingdong.utils.imageLoader;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;


/**
 * Created by Dash on 2017/12/6.
 */
public class DashApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainId;
    public static boolean isLoginSuccess;//是否已经登录的状态


    @Override
    public void onCreate() {
        super.onCreate();
//在应用创建的时候,,,对imageLoader进行全局配置
        ImageLoaderUtil.init(this);//写一个工具类对imageLoader进行初始化...应用也可以作为上下文
        //关于context----http://blog.csdn.net/lmj623565791/article/details/40481055
        context = getApplicationContext();
        //初始化handler
        handler = new Handler();
        //主线程的id
        mainId = Process.myTid();


    }

    /**
     * 对外提供了context
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * 得到全局的handler
     * @return
     */
    public static Handler getAppHanler() {
        return handler;
    }

    /**
     * 获取主线程id
     * @return
     */
    public static int getMainThreadId() {
        return mainId;
    }
}
