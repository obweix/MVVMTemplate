package com.example.mvvmapplication.ui.dashboard.bigimage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvvmapplication.databinding.FragmentBigImageBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;
import com.example.mvvmapplication.ui.widgets.BigImage;

import java.io.IOException;
import java.io.InputStream;

public class BigImageFragment extends BaseFragment {
    private FragmentBigImageBinding mBinding;
    private BigImage mBigImage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = FragmentBigImageBinding.inflate(inflater, container, false);

        mBigImage = mBinding.bigImage;
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("big_image.jpg");
            mBigImage.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return mBinding.getRoot();

    }
}
