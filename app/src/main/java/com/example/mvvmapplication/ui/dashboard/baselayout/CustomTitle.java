package com.example.mvvmapplication.ui.dashboard.baselayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mvvmapplication.R;

public class CustomTitle extends LinearLayout {
    public CustomTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_title,this);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnEdit = findViewById(R.id.btn_edit);

        btnBack.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"you click back button",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnEdit.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"you click edit button",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
