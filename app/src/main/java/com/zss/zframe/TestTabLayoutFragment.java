package com.zss.zframe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;

/**
 * Created by zm on 17/7/20.
 */
public class TestTabLayoutFragment extends BaseFragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tablayout;
    }

    @Override
    public void initView() {
        super.initView();
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            private String[] mTitles = new String[]{ "首页", "功能", "我的" };

            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new TestRecyclerAdapterFragment();
                } else if (position == 1) {
                    return new TestRecyclerAdapterFragment();
                }else{
                    return new TestRecyclerAdapterFragment();
                }
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("TabLayout");
    }
}
