package com.example.mvvmapplication.bean.network.service;

import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.bean.BaseBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

public interface AlbumsService {
    @GET("master/albums_list.json")
    Flowable<BaseBean<List<Albums>>> getAlbums();
}
