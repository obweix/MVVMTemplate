package com.example.mvvmapplication.ui.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mvvmapplication.databinding.FragmentTestBinding;

public class TestFragment extends Fragment {

    FragmentTestBinding binding;
    private static final String TAG = "TestFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ------------------------------");
        super.onDestroy();
    }
}
