package bwie.com.jingdong.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dell on 2017/12/5.
 */

public class StringUtil {
    /**
     * 把一个字节流转成字符串
     *
     * @param inputStream
     * @param charset
     * @return
     */
    public static String streamToString(InputStream inputStream, String charset) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }


            bufferedReader.close();
            return builder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return  null;
    }
}
