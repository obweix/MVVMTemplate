package com.example.mvvmapplication.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmapplication.ui.base.BaseRecyclerAdapter;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}