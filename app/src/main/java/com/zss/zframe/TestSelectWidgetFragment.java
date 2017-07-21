package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonSelectWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zm on 16/8/5.
 */
public class TestSelectWidgetFragment extends BaseFragment{

    private CommonSelectWidget widget1;
    private CommonSelectWidget widget2;
    private CommonSelectWidget widget3;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_select_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonSelectWidget)findViewById(R.id.widget1);
        widget2 = (CommonSelectWidget)findViewById(R.id.widget2);
        widget3 = (CommonSelectWidget)findViewById(R.id.widget3);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setLeftText("类似系统Spinner");
        widget1.setData(getDatas());

        widget2.setLeftText("单选组件");
        widget2.setData(getDatas(), 1);

        List<Integer> defaultSelect = new ArrayList<>();
        defaultSelect.add(1);
        defaultSelect.add(2);
        widget3.setLeftText("多选组件");
        widget3.setData(getDatas(), defaultSelect);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonSelectWidget");
        Bundle args = getArguments();
        if(args != null && args.getBoolean("flag")){
            toolbar.inflateMenu(R.menu.menu_add);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_add:
                            TestDialogFragment fragment = new TestDialogFragment();
                            fragment.setArguments(getArguments());
                            addFragment(fragment);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public List<String> getDatas(){
        String [] titles = new String[]{
                "ListItem1",
                "ListItem2",
                "ListItem3",
                "ListItem4"
        };
        return Arrays.asList(titles);
    }

}
