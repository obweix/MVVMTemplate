package com.example.mvvmapplication.ui.home;

import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.bean.network.RetrofitClient;
import com.example.mvvmapplication.bean.network.service.AlbumsService;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class HomeModel implements IHomeModel {

    @Override
    public Flowable<BaseBean<List<Albums>>> getAlbums() {
        return RetrofitClient.getInstance().getService(AlbumsService.class).getAlbums();
    }
}
