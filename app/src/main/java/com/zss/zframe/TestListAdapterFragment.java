package com.zss.zframe;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.zss.library.adapter.listview.CommonAdapter;
import com.zss.library.adapter.listview.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 16/8/5.
 */
public class TestListAdapterFragment extends BaseFragment{

    private ListView listView;
    private CommonAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_listview;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView)findViewById(R.id.list_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<String>(getActivity(), R.layout.item_listview) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
            }
        };
        listView.setAdapter(adapter);
        adapter.addAll(getData());
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("ListView");
    }

    public List<String> getData(){
        List<String> list = new ArrayList<>();
        for(int i=0; i<100; i++){
            list.add("张三水"+i);
        }
        return list;
    }
}
