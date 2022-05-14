package com.example.mvvmapplication.ui.home;

import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.bean.Goods;
import com.example.mvvmapplication.bean.network.RetrofitClient;
import com.example.mvvmapplication.bean.network.service.GoodsService;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class HomeModel implements IHomeModel {
    @Override
    public Flowable<BaseBean<List<Goods>>> getData() {
        return RetrofitClient.getInstance().getService(GoodsService.class).getGoods();
    }
}
