package com.example.mvvmapplication.ui.home;

import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.bean.Goods;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IHomeModel {
    Flowable<BaseBean<List<Goods>>> getData();
}
