package com.example.mvvmapplication.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.bean.Goods;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private MutableLiveData<String> mText;

    private MutableLiveData<List<Goods>> mGoods;

    private IHomeModel mHomeModel;

    Disposable mGetGoodsFromNetWorkDisposable;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mGoods = new MutableLiveData<>();

        mHomeModel = new HomeModel();

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Goods>> getGoods(){
         return mGoods;
    }

    public void getGoodsFromNetWork(){
         mGetGoodsFromNetWorkDisposable = mHomeModel.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<Goods>>>() {
                    @Override
                    public void accept(BaseBean<List<Goods>> listBaseBean) throws Throwable {
                        mGoods.setValue(listBaseBean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "accept: get goods error");
                        mGoods.setValue(null);
                    }
                });
    }

    public void cancelGetGoodsFromNetwork(){
        if(null != mGetGoodsFromNetWorkDisposable && !mGetGoodsFromNetWorkDisposable.isDisposed()){
            mGetGoodsFromNetWorkDisposable.dispose();
            mGetGoodsFromNetWorkDisposable = null;
        }
    }

}