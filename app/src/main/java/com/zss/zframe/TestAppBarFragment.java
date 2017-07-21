package com.zss.zframe;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;

/**
 * Created by zm on 16/8/5.
 */
public class TestAppBarFragment extends BaseFragment{

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_appbar;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setCenterTitle("中间对齐");
        toolbar.setCenterSubtitle("副标题");
        toolbar.setRightText("确认");
    }
}
