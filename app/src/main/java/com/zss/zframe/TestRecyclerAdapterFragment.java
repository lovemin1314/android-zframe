package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zss.library.adapter.recyclerview.CommonAdapter;
import com.zss.library.adapter.recyclerview.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 16/8/5.
 */
public class TestRecyclerAdapterFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private CommonAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initView() {
        super.initView();
        LogUtils.i("test", "-----------initView-------");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
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
        for(int i=0; i<100; i++){
            list.add("张三水"+i);
        }
        return list;
    }
}
