package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonEditWidget;

/**
 * Created by zm on 16/8/5.
 */
public class TestEditWidgetFragment extends BaseFragment{

    private CommonEditWidget widget1;
    private CommonEditWidget widget2;
    private CommonEditWidget widget3;
    private CommonEditWidget widget4;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonEditWidget)findViewById(R.id.widget1);
        widget2 = (CommonEditWidget)findViewById(R.id.widget2);
        widget3 = (CommonEditWidget)findViewById(R.id.widget3);
        widget4 = (CommonEditWidget)findViewById(R.id.widget4);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setHint("手机号");
        widget1.setLeftImageResource(R.mipmap.ic_collections_black);

        widget2.setHint("密码");
        widget2.setLeftImageResource(R.mipmap.ic_camera_black);

        widget3.setHint("验证码");
        widget3.setLeftImageResource(R.mipmap.ic_camera_alt_black);
        Button rightBtn = new Button(getContext());
        rightBtn.setText("发送");
        widget3.setRightView(rightBtn);

        widget4.setHint("测试EditWidget");

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonEditWidget");
        Bundle args = getArguments();
        if(args != null && args.getBoolean("flag")){
            toolbar.inflateMenu(R.menu.menu_add);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_add:
                            TestSelectWidgetFragment fragment = new TestSelectWidgetFragment();
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
