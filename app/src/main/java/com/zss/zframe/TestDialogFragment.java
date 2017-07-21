package com.zss.zframe;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonTextWidget;

/**
 * Created by zm on 16/8/5.
 */
public class TestDialogFragment extends BaseFragment{

    private CommonTextWidget widget1;
    private CommonTextWidget widget2;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dialog_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonTextWidget)findViewById(R.id.widget1);
        widget2 = (CommonTextWidget)findViewById(R.id.widget2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        widget1.setLeftText("标准对话框");
        widget1.setRightImageResource(R.mipmap.right_arrow);
        widget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog(getActivity());
                dialog.setTitle("提示");
                dialog.setContentText("我是一个标准对话框");
                dialog.setOnClickConfirmListener("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //处理事情
                    }
                });
                dialog.show();
            }
        });

        widget2.setLeftText("自定义中间对话框");
        widget2.setRightImageResource(R.mipmap.right_arrow);
        widget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog(getActivity());
                dialog.setTitle("提示");
                dialog.setMiddleView(R.layout.dialog_center_view);
                dialog.setOnClickConfirmListener("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //处理事情
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonDialog");
        Bundle args = getArguments();
        if(args != null && args.getBoolean("flag")){
            toolbar.inflateMenu(R.menu.menu_add);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_add:
                            TestProgressDialogFragment fragment = new TestProgressDialogFragment();
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
