package com.example.mvvmapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.bean.BaseBean;
import com.example.mvvmapplication.bean.network.RetrofitClient;
import com.example.mvvmapplication.bean.network.service.AlbumsService;

import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        AlbumsService albumsService = RetrofitClient.getInstance().getService(AlbumsService.class);

        albumsService.getAlbums().subscribe(new Consumer<BaseBean<List<Albums>>>() {
                                              @Override
                                              public void accept(BaseBean<List<Albums>> listBaseBean) throws Throwable {
                                                  System.out.println(listBaseBean.toString());
                                              }
                                          }, new Consumer<Throwable>() {
                                              @Override
                                              public void accept(Throwable throwable) throws Throwable {
                                                  System.out.println("不抛出异常");
                                              }
                                          }
        );


        while (true){}

    }
}