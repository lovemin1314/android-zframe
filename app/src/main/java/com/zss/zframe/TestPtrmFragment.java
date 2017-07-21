package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zss.library.adapter.recyclerview.CommonAdapter;
import com.zss.library.adapter.recyclerview.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.ptrm.PtlmDefaultHandler;
import com.zss.library.ptrm.PtrClassicFrameLayout;
import com.zss.library.ptrm.PtrFrameLayout;
import com.zss.library.appbar.CommonAppBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 16/8/5.
 */
public class TestPtrmFragment extends BaseFragment{

    private PtrClassicFrameLayout ptrFrameLayout;
    private RecyclerView recyclerView;
    private CommonAdapter adapter;
    private int index = 0;
    private int count = 20;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ptrm;
    }

    @Override
    public void initView() {
        super.initView();
        ptrFrameLayout = (PtrClassicFrameLayout)findViewById(R.id.ptrFrameLayout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrFrameLayout.setPtrHandler(new PtlmDefaultHandler() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                index++;
                adapter.addAll(getData());
                ptrFrameLayout.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                index = 0;
                adapter.replaceAll(getData());
                ptrFrameLayout.refreshComplete();
            }
        });
        adapter = new CommonAdapter<String>(R.layout.item_recyclerview) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
            }

            @Override
            protected void onItemClick(View view, String item, int position) {

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.addAll(getData());
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("RecyclerView");
    }

    public List<String> getData(){
        List<String> list = new ArrayList<>();
        int i = index * 10;
        int total = i + count;
        for(; i< total; i++){
            list.add("张三水"+i);
        }
        return list;
    }
}
