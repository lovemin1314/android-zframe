package com.zss.zframe;

import android.os.Bundle;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonSwitchWidget;

/**
 * Created by zm on 16/8/5.
 */
public class TestSwitchWidgetFragment extends BaseFragment{

    private CommonSwitchWidget widget1;
    private CommonSwitchWidget widget2;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_switch_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonSwitchWidget)findViewById(R.id.widget1);
        widget2 = (CommonSwitchWidget)findViewById(R.id.widget2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setLeftText("默认开");
        widget1.setChecked(true);

        widget2.setLeftText("默认关");
        widget2.setChecked(false);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonSwitchWidget");
    }

}
