package com.example.mvvmapplication.ui.dashboard.baselayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmapplication.databinding.FragmentBaseLayoutBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;

public class BaseLayoutFragment extends BaseFragment {
    private FragmentBaseLayoutBinding mBinding;
    private BaseLayoutViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mBinding = FragmentBaseLayoutBinding.inflate(inflater,container,false);
       mViewModel = new ViewModelProvider(this).get(BaseLayoutViewModel.class);


       View root = mBinding.getRoot();

       return root;

    }
}
