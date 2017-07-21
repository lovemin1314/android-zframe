package com.zss.zframe;

import android.os.Bundle;
import android.view.View;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonProgressDialog;
import com.zss.library.widget.CommonTextWidget;

/**
 * Created by zm on 16/8/5.
 */
public class TestProgressDialogFragment extends BaseFragment{

    private CommonTextWidget widget1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_progress_dialog_widget;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonTextWidget)findViewById(R.id.widget1);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        widget1.setLeftText("加载对话框");
        widget1.setRightImageResource(R.mipmap.right_arrow);
        widget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonProgressDialog dialog = new CommonProgressDialog(getActivity());
                dialog.show();
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonProgressDialog");
    }
}
