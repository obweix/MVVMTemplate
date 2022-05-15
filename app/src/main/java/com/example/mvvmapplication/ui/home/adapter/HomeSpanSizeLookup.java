package com.example.mvvmapplication.ui.home.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mvvmapplication.bean.Albums;

import java.util.List;

public class HomeSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private final List<Albums> data;

    public HomeSpanSizeLookup(List<Albums> data){
        this.data = data;
    }

    @Override
    public int getSpanSize(int position) {
        return data.get(position).getSpanSize();
    }

    public void setAlbums(List<Albums> albums){
        this.data.clear();
        this.data.addAll(albums);
    }
}
