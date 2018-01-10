package bwie.com.jingdong.presenter;


import bwie.com.jingdong.view.IView.IMainActivity;
import bwie.com.jingdong.model.CartModel;
import bwie.com.jingdong.presenter.interfac.ICartPresenter;

/**
 * Created by Dash on 2017/12/12.
 */
public class CartPresenter implements ICartPresenter {

    private final CartModel cartModel;
    private IMainActivity iMainActivity;

    public CartPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        cartModel = new CartModel(this);
    }

    public void getCartData(String cartUrl) {
        cartModel.getCartData(cartUrl);

    }

    @Override
    public void getSuccessCartJson(String json) {
        //回调给view
        iMainActivity.getSuccessCartData(json);
    }
}
