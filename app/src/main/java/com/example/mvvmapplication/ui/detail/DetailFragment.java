package com.example.mvvmapplication.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmapplication.databinding.FragmentAlbumsDetailBinding;


public class DetailFragment extends Fragment {
    private DetailViewModel detailViewModel;
    private FragmentAlbumsDetailBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        binding = FragmentAlbumsDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.detailTextview;
        detailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        Log.d("TAG", "onCreateView: " + getArguments().getInt("GOODS_ID"));

        detailViewModel.setText( Integer.toString(getArguments().getInt("ALBUMS_ID")));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
