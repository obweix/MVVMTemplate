package com.example.mvvmapplication.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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




    private void init() {
        binding.homeSwiperefresh.setOnRefreshListener(this);

        List<Albums> albums = new ArrayList<Albums>();


        if(null == homeViewModel.homeSpanSizeLookup ){
            homeViewModel.homeSpanSizeLookup = new HomeSpanSizeLookup(albums);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(homeViewModel.homeSpanSizeLookup);
        binding.homeRecyclerview.setLayoutManager(gridLayoutManager);

       if(null == homeViewModel.homeRecyclerViewAdapter){
           homeViewModel.homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(binding.homeRecyclerview,getActivity(),albums);
           homeViewModel.homeRecyclerViewAdapter.setOnItemClickListener(this);
           Log.d(TAG, "init: new recycler view adapter");
       }

        binding.homeRecyclerview.setAdapter(homeViewModel.homeRecyclerViewAdapter);



        homeViewModel.getAlbums().observe(getViewLifecycleOwner(), new Observer<List<Albums>>() {
            @Override
            public void onChanged(List<Albums> albums) {
                homeViewModel.homeSpanSizeLookup.setAlbums(albums);
                homeViewModel.homeRecyclerViewAdapter.setAlbums(albums);
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

        if(homeViewModel == null){
            homeViewModel =
                    new ViewModelProvider(this).get(HomeViewModel.class);
            Log.d(TAG, "onCreateView: homeViewModel is null");
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        init();

        Log.d(TAG, "onCreateView: -----------");

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        homeViewModel.cancelGetAlbumsFromNetwork();
        super.onDestroyView();

        Log.d(TAG, "onDestroyView: -------------");
        
        binding = null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy:");
        if(homeViewModel != null){
            homeViewModel.homeRecyclerViewAdapter = null;
            homeViewModel.homeSpanSizeLookup = null;
        }

        super.onDestroy();
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