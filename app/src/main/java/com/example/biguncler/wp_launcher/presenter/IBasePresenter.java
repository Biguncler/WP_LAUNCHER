package com.example.biguncler.wp_launcher.presenter;

import com.example.biguncler.wp_launcher.mvpview.IBaseView;

/**
 * Created by Biguncler on 2/1/2019.
 */

public interface IBasePresenter {
    void detachView();
    void attachView(IBaseView view);
}
