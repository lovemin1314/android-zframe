package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonTextWidget;

/**
 * Created by zm on 16/8/5.
 */
public class TestTextWidgetFragment extends BaseFragment{

    private CommonTextWidget widget1;
    private CommonTextWidget widget2;
    private CommonTextWidget widget3;
    private CommonTextWidget widget4;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_text_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonTextWidget)findViewById(R.id.widget1);
        widget2 = (CommonTextWidget)findViewById(R.id.widget2);
        widget3 = (CommonTextWidget)findViewById(R.id.widget3);
        widget4 = (CommonTextWidget)findViewById(R.id.widget4);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        widget1.setLeftText("手机号");
        widget1.setLeftImageResource(R.mipmap.ic_collections_black);
        widget1.setRightText("18033419527");
        widget1.setRightImageResource(R.mipmap.right_arrow);

        widget2.setLeftText("密码");
        widget2.setRightText("zhangmiao945");
        widget2.setLeftImageResource(R.mipmap.ic_camera_black);
        widget2.setRightImageResource(R.mipmap.right_arrow);

        widget3.setLeftText("验证码");
        widget3.setRightText("9227");
        widget3.setLeftImageResource(R.mipmap.ic_camera_alt_black);
        widget3.setRightImageResource(R.mipmap.right_arrow);

        widget4.setLeftText("测试TextWidget");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonTextWidget");
        Bundle args = getArguments();
        if(args != null && args.getBoolean("flag")){
            toolbar.inflateMenu(R.menu.menu_add);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_add:
                            TestEditWidgetFragment fragment = new TestEditWidgetFragment();
                            fragment.setArguments(getArguments());
                            addFragment(fragment);
                            break;
                    }
                    return true;
                }
            });
        }
    }
}
