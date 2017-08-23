package com.zss.library.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zss.library.R;
import com.zss.library.adapter.BrowserPagerAdapter;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.viewpager.BrowserPager;

import java.util.List;

/**
 * Created by zm on 2017/3/29.
 */

public class BrowserActivity extends BaseActivity {

    private TextView hint;
    private BrowserPager viewPager;
    private BrowserPagerAdapter adapter;
    private int loadType;
    private int position;
    private List<?> datas;
    public final static String EXTRA_POSITION = "postion"; //默认位置
    public final static String EXTRA_DATAS = "datas"; //图片加载路径
    public final static String EXTRA_LOAD_TYPE = "type"; //图片加载类型

    public final static int URL = 1;
    public final static int RES = 2;
    public final static int FILE = 3;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    public void initView() {
        super.initView();
        setBackgroundResource(R.color.black);
        hint = (TextView)findViewById(R.id.hint);
        viewPager = (BrowserPager)findViewById(R.id.viewPager);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        setStatusBarColor(getColorValue(R.color.black));
        removeAppBar();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        //EXTRA_LOAD_TYPE设置RES，intent需要设置putIntegerArrayListExtra(EXTRA_DATAS, xxx);
        //EXTRA_LOAD_TYPE设置URL、FILE，intent需要设置putStringArrayListExtra(EXTRA_DATAS, xxx);

        loadType = getIntent().getIntExtra(EXTRA_LOAD_TYPE, URL);
        if(loadType == RES){
            datas = getIntent().getIntegerArrayListExtra(EXTRA_DATAS);
        }else{
            datas = getIntent().getStringArrayListExtra(EXTRA_DATAS);
        }
        adapter = new BrowserPagerAdapter(getActivity(), datas, loadType);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
                hint.setText(position + 1 + "/" + datas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        hint.setText(position + 1 + "/" + datas.size());
    }

}
