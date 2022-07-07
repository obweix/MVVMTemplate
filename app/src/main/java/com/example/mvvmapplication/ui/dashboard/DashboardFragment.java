package com.example.mvvmapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.databinding.FragmentDashboardBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;
import com.example.mvvmapplication.ui.base.BaseRecyclerAdapter;
import com.example.mvvmapplication.ui.base.RecyclerViewHolder;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends BaseFragment {

    private DashboardViewModel dashboardViewModel;

    private FragmentDashboardBinding binding;

    RecyclerView mRecyclerView;


    private BaseRecyclerAdapter<String> mAdapter;

    QMUIPullLayout mPullLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mPullLayout = binding.pullLayout;

        mRecyclerView = binding.recyclerView;


        initData();

        return root;
    }

    private void initData() {

        mPullLayout.setActionListener(new QMUIPullLayout.ActionListener() {
            @Override
            public void onActionTriggered(@NonNull QMUIPullLayout.PullAction pullAction) {
                mPullLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
                            onRefreshData();
                        } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
                            onLoadMore();
                        }
                        mPullLayout.finishActionRun(pullAction);
                    }
                }, 3000);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mAdapter = new BaseRecyclerAdapter<String>(getContext(), null) {
            @Override
            public int getItemLayoutId(int viewType) {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            public void bindData(@NonNull RecyclerViewHolder holder, int position, @NonNull String item) {
                holder.setText(android.R.id.text1, item);
            }
        };
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(getContext(), "click position=" + pos, Toast.LENGTH_SHORT).show();
                if(0 == pos){
                    navigate(getView(),R.id.navigation_dashboard,R.id.navigation_base_layout,null);
                }
                if(1 == pos){
                    navigate(getView(),R.id.navigation_dashboard,R.id.navigation_chart,null);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        onDataLoaded();
    }

    private void onDataLoaded() {
//        List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
//                "Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
        List<String> data= new ArrayList<>();
        data.add("基础布局");
        data.add("图表");

//        Collections.shuffle(data);
        mAdapter.setData(data);
    }

    private void onRefreshData() {
        List<String> data = new ArrayList<>();
        long id = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            data.add("onRefreshData-" + id + "-" + i);
        }
        mAdapter.prepend(data);
        mRecyclerView.scrollToPosition(0);
    }

    private void onLoadMore() {
        List<String> data = new ArrayList<>();
        long id = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            data.add("onLoadMore-" + id + "-" + i);
        }
        mAdapter.append(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}