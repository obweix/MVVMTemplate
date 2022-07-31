package com.example.mvvmapplication.ui.dashboard.baselayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.TestActivity;
import com.example.mvvmapplication.databinding.FragmentBaseLayoutBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

public class BaseLayoutFragment extends BaseFragment {
    private FragmentBaseLayoutBinding mBinding;
    private BaseLayoutViewModel mViewModel;
    private QMUIPopup mNormalPopup;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mBinding = FragmentBaseLayoutBinding.inflate(inflater,container,false);
       mViewModel = new ViewModelProvider(this).get(BaseLayoutViewModel.class);
       mBinding.openDialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BaseDialog baseDialog = new BaseDialog();
               baseDialog.show(getActivity().getSupportFragmentManager(),"BaseLayoutFragment");
           }
       });

       mBinding.openBottomSheet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showSimpleBottomSheetList(
                       false, false, false, null,
                       3, false, false);
           }
       });

       mBinding.showPopups.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showPopups(v);
           }
       });

       mBinding.test4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                startActivity(new Intent(getContext(), TestActivity.class));
           }
       });


       View root = mBinding.getRoot();

       return root;

    }

    // ================================ 生成不同类型的BottomSheet
    private void showSimpleBottomSheetList(boolean gravityCenter,
                                           boolean addCancelBtn,
                                           boolean withIcon,
                                           CharSequence title,
                                           int itemCount,
                                           boolean allowDragDismiss,
                                           boolean withMark) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        builder.setGravityCenter(gravityCenter)
                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                .setTitle(title)
                .setAddCancelBtn(addCancelBtn)
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Item " + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                });
        if(withMark){
            builder.setCheckedIndex(40);
        }
        for (int i = 1; i <= itemCount; i++) {
            if(withIcon){
                builder.addItem(ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_lab), "Item " + i);
            }else{
                builder.addItem("Item " + i);
            }

        }
        builder.build().show();
    }

    private void showPopups(View v){
        TextView textView = new TextView(getContext());
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(getContext(), 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("QMUIBasePopup 可以设置其位置以及显示和隐藏的动画");
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        QMUISkinHelper.setSkinValue(textView, builder);
        builder.release();
        mNormalPopup = QMUIPopups.popup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 250))
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .view(textView)
                .skinManager(QMUISkinManager.defaultInstance(getContext()))
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
                .offsetX(QMUIDisplayHelper.dp2px(getContext(), 20))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(getContext(), 5))
                .shadow(true)
                .arrow(true)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(v);
    }
}
