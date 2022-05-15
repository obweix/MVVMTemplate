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
import com.example.mvvmapplication.bean.Albums;
import com.example.mvvmapplication.databinding.FragmentHomeBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;
import com.example.mvvmapplication.ui.home.adapter.HomeRecyclerViewAdapter;
import com.example.mvvmapplication.ui.home.adapter.HomeSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,HomeRecyclerViewAdapter.OnItemClickListener{
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private HomeSpanSizeLookup homeSpanSizeLookup;


    private void init() {
        binding.homeSwiperefresh.setOnRefreshListener(this);

        List<Albums> albums = new ArrayList<Albums>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        homeSpanSizeLookup = new HomeSpanSizeLookup(albums);
        gridLayoutManager.setSpanSizeLookup(homeSpanSizeLookup);
        binding.homeRecyclerview.setLayoutManager(gridLayoutManager);

        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(binding.homeRecyclerview,getActivity(),albums);
        homeRecyclerViewAdapter.setOnItemClickListener(this);
        binding.homeRecyclerview.setAdapter(homeRecyclerViewAdapter);

        homeViewModel.getAlbums().observe(getViewLifecycleOwner(), new Observer<List<Albums>>() {
            @Override
            public void onChanged(List<Albums> albums) {
                homeSpanSizeLookup.setAlbums(albums);
                homeRecyclerViewAdapter.setAlbums(albums);
                binding.homeSwiperefresh.setRefreshing(false);

                binding.loadingText.setVisibility(View.INVISIBLE);
            }
        });


        if(homeViewModel.getAlbums().getValue() == null){
            homeViewModel.getAlbumsFromNetWork();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        homeViewModel.cancelGetAlbumsFromNetwork();
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void onRefresh() {
        homeViewModel.getAlbumsFromNetWork();
    }

    @Override
    public void onItemClick(Albums Albums) {
        Bundle bundle = new Bundle();
        bundle.putInt("ALBUMS_ID",Albums.getAlbumsId());
        navigate(getView(),R.id.navigation_home,R.id.navigation_detial,bundle);
    }
}