package com.example.mvvmapplication.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is detail fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String text){
        mText.setValue(text);
    }

}
