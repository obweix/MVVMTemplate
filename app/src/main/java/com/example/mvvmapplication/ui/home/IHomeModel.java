package com.example.mvvmapplication.ui.home;

import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.bean.BaseBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IHomeModel {
    Flowable<BaseBean<List<Albums>>> getAlbums();
}
