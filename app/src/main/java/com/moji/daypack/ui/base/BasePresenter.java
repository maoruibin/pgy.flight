package com.moji.daypack.ui.base;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/8/16
 * Time: 11:49 PM
 * Desc: BasePresenter
 */
public interface BasePresenter<T> {

    void subscribe(T param);

    void unsubscribe();
}
