package bwie.com.jingdong.model.bean;

import java.io.Serializable;

/**
 * Created by Dash on 2017/12/12.
 */
public class CountPriceBean implements Serializable{
    private double price;
    private int count;

    public CountPriceBean(double price, int count) {
        this.price = price;
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
