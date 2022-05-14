package com.example.mvvmapplication.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.bean.Goods;
import com.example.mvvmapplication.databinding.FragmentHomeBinding;
import com.example.mvvmapplication.ui.home.adapter.HomeRecyclerViewAdapter;
import com.example.mvvmapplication.ui.home.adapter.HomeSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,HomeRecyclerViewAdapter.OnItemClickListener{
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private HomeSpanSizeLookup homeSpanSizeLookup;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.homeSwiperefresh.setOnRefreshListener(this);

        List<Goods> goods = new ArrayList<Goods>();
        gridLayoutManager = new GridLayoutManager(getActivity(),4);
        homeSpanSizeLookup = new HomeSpanSizeLookup(goods);
        gridLayoutManager.setSpanSizeLookup(homeSpanSizeLookup);
        binding.homeRecyclerview.setLayoutManager(gridLayoutManager);

        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(binding.homeRecyclerview,getActivity(),goods);
        homeRecyclerViewAdapter.setOnItemClickListener(this);
        binding.homeRecyclerview.setAdapter(homeRecyclerViewAdapter);


        homeViewModel.getGoods().observe(getViewLifecycleOwner(), new Observer<List<Goods>>() {
            @Override
            public void onChanged(List<Goods> goods) {
                homeSpanSizeLookup.setGoods(goods);
                homeRecyclerViewAdapter.setGoods(goods);
                binding.homeSwiperefresh.setRefreshing(false);
            }
        });


        if(homeViewModel.getGoods().getValue() == null){
            homeViewModel.getGoodsFromNetWork();
        }
    }

    @Override
    public void onDestroyView() {
        homeViewModel.cancelGetGoodsFromNetwork();

        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRefresh() {
        homeViewModel.getGoodsFromNetWork();
    }

    @Override
    public void onItemClick(Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putInt("GOODS_ID",goods.getGoodsId());
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home,false)
                .setEnterAnim(R.anim.slide_in_right)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setExitAnim(R.anim.slide_out_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        Navigation.findNavController(getView()).navigate(R.id.navigation_detial,bundle,navOptions);
    }
}