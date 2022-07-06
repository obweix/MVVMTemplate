package com.example.mvvmapplication.ui.home;

import android.accounts.NetworkErrorException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.ui.home.adapter.HomeRecyclerViewAdapter;
import com.example.mvvmapplication.ui.home.adapter.HomeSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    private MutableLiveData<String> mText;

    private MutableLiveData<List<Albums>> mAlbums;

    private IHomeModel mHomeModel;

    Disposable mGetGoodsFromNetWorkDisposable;

    public HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    public HomeSpanSizeLookup homeSpanSizeLookup;



    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mAlbums = new MutableLiveData<>();

        mHomeModel = new HomeModel();

        Log.d(TAG, "HomeViewModel: is construct");

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Albums>> getAlbums(){
        return mAlbums;
    }


    public void getAlbumsFromNetWork(){
            mHomeModel.getAlbums().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseBean<List<Albums>>>() {
                        @Override
                        public void accept(BaseBean<List<Albums>> listBaseBean) throws Throwable {
                            mAlbums.setValue(listBaseBean.getData());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            Log.d(TAG, "accept: get goods error");
                            mAlbums.setValue(new ArrayList<>());
                        }
                    });
    }

    public void cancelGetAlbumsFromNetwork(){
        if(null != mGetGoodsFromNetWorkDisposable && !mGetGoodsFromNetWorkDisposable.isDisposed()){
            mGetGoodsFromNetWorkDisposable.dispose();
            mGetGoodsFromNetWorkDisposable = null;
        }
    }

}