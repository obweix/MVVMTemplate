package com.example.mvvmapplication.ui.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmapplication.R;
import com.example.mvvmapplication.bean.Goods;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Goods> data;
    private RecyclerView recyclerView;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public HomeRecyclerViewAdapter(RecyclerView recyclerView, Context context, List<Goods> data){
        this.context = context;
        this.data = data;
        this.recyclerView = recyclerView;

        layoutInflater = LayoutInflater.from(context);


    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            int position = recyclerView.getChildAdapterPosition(v);
            listener.onItemClick(data.get(position));
        }
    }

    public void setGoods( List<Goods> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  layoutInflater.inflate(viewType,parent,false);
        view.setOnClickListener(this);
        if(viewType == R.layout.home_recycler_text_image){
            return new MultiViewHolder(view);
        }

        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Goods goods = data.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case R.layout.home_recycler_banner:
                ((Banner) holder.itemView).setAdapter(new BannerImageAdapter<String>(goods.getBanners()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                        Glide.with(holder.itemView)
                                .load(data)
                                .apply(RequestOptions.centerCropTransform())
                                .into(holder.imageView);
                    }
                }).addBannerLifecycleObserver((LifecycleOwner) context)
                        .setIndicator(new CircleIndicator(context));
                break;
            case R.layout.home_recycler_text_image:
                MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
                multiViewHolder.textview.setText(goods.getText());
                Glide.with(holder.itemView)
                        .load(goods.getImageUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into(multiViewHolder.imageView);
                break;
            case R.layout.home_recycler_text:
                ((TextView)holder.itemView).setText(goods.getText());
                break;
            case R.layout.home_recycler_image:
                Glide.with(holder.itemView)
                        .load(goods.getImageUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into((ImageView) holder.itemView);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Goods goods = data.get(position);
        if(goods.getBanners() != null){
            return R.layout.home_recycler_banner;
        }else if(goods.getImageUrl() == null){
            return  R.layout.home_recycler_text;
        }else if(goods.getText() == null){
            return R.layout.home_recycler_image;
        }else{
            return R.layout.home_recycler_text_image;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder{

        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MultiViewHolder  extends RecyclerView.ViewHolder{
        TextView textview;
        ImageView imageView;

        public MultiViewHolder(@NonNull View itemView) {

            super(itemView);
            textview = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.listener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(Goods goods);
    }
}
