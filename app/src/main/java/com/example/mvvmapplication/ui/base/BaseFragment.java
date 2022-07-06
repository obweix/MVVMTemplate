package com.example.mvvmapplication.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.mvvmapplication.R;

public abstract class BaseFragment extends Fragment {

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    public void navigate(android.view.View view,@IdRes int from,@IdRes int to, @Nullable android.os.Bundle args){
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setExitAnim(R.anim.slide_out_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .setPopUpTo(from,false)
                .build();

            Navigation.findNavController(view).navigate(to,args,navOptions);
    }
}
