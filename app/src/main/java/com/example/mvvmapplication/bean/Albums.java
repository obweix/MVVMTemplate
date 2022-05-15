package com.example.mvvmapplication.bean;

/**
 * Copyright 2022 bejson.com
 */
import java.util.List;

/**
 * Auto-generated: 2022-05-15 19:14:18
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Albums {

    private int albumsId;
    private String text;
    private String imageUrl;
    private int spanSize;
    private List<String> banners;
    public void setAlbumsId(int albumsId) {
        this.albumsId = albumsId;
    }
    public int getAlbumsId() {
        return albumsId;
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
                "albumsId=" + albumsId +
                ", text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", spanSize=" + spanSize +
                ", banners=" + banners +
                '}';
    }
}