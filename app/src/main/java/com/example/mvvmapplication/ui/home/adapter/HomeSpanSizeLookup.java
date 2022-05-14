package com.example.mvvmapplication.ui.home.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mvvmapplication.bean.Goods;

import java.util.List;

public class HomeSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private final List<Goods> data;

    public HomeSpanSizeLookup(List<Goods> data){
        this.data = data;
    }

    @Override
    public int getSpanSize(int position) {
        return data.get(position).getSpanSize();
    }

    public void setGoods(List<Goods> goods){
        this.data.clear();
        this.data.addAll(goods);
    }
}
