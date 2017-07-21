package com.zss.zframe;

import android.os.Bundle;
import android.view.View;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.widget.CommonTextWidget;
import com.zss.library.widget.CommonWheelDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 16/8/5.
 */
public class TestWheelDialogFragment extends BaseFragment{

    private CommonTextWidget widget1;
    private CommonTextWidget widget2;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wheel_dialog_widget;
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

        widget1.setLeftText("单滚轮效果");
        widget1.setRightImageResource(R.mipmap.right_arrow);
        widget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] arr = new String[]{"深圳", "北京", "上海", "广州"};
                CommonWheelDialog dialog = new CommonWheelDialog(getActivity(), arr);
                dialog.show();
            }
        });

        widget2.setLeftText("多滚轮效果");
        widget2.setRightImageResource(R.mipmap.right_arrow);
        widget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] hour = new String[24];
                for(int i = 1; i<=24; i++){
                    hour[i-1] = i+"H";
                }
                String [] minute = new String[60];
                for(int i = 1; i<=60; i++){
                    minute[i-1] = i+"M";
                }
                String [] second = new String[60];
                for(int i = 1; i<=60; i++){
                    second[i-1] = i+"S";
                }
                List<String[]> list = new ArrayList<String[]>();
                list.add(hour);
                list.add(minute);
                list.add(second);
                CommonWheelDialog dialog = new CommonWheelDialog(getActivity(), list);
                dialog.show();
            }
        });

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonWheelDialog");
    }
}
