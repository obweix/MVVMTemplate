package com.example.mvvmapplication.bean;

import java.util.List;

/**
 * Auto-generated: 2022-05-03 15:24:59
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Goods {

    private int goodsId;
    private String text;
    private String imageUrl;
    private int spanSize;
    private List<String> banners;
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    public int getGoodsId() {
        return goodsId;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }
    public int getSpanSize() {
        return spanSize;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }
    public List<String> getBanners() {
        return banners;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", spanSize=" + spanSize +
                ", banners=" + banners +
                '}';
    }
}