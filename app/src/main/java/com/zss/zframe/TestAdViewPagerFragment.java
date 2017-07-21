package com.zss.zframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zss.library.activity.BrowserActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.viewpager.AdViewPager;
import com.zss.library.viewpager.CycleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 17/7/20.
 */
public class TestAdViewPagerFragment extends BaseFragment{

    private AdViewPager adViewPager;
    private CycleViewPager cycleViewPager;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void initView() {
        super.initView();
        adViewPager = (AdViewPager)findViewById(R.id.adViewPager);
        cycleViewPager = (CycleViewPager)findViewById(R.id.cycleViewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        //设置广告
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.bz1);
        list.add(R.mipmap.bz2);
        list.add(R.mipmap.bz3);
        list.add(R.mipmap.bz4);
        list.add(R.mipmap.bz5);
        adViewPager.setResIds(list);

        //设置轮播图片
        cycleViewPager.setAdapter(new MyPagerAdapter(list));
        cycleViewPager.setNoAutoPlay();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("ViewPager");
    }

    class MyPagerAdapter extends PagerAdapter {

        List<Integer> resIds;

        public MyPagerAdapter(List<Integer> list){
            this.resIds = list;
        }

        @Override
        public int getCount() {
            return resIds.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width = ViewPager.LayoutParams.MATCH_PARENT;
            lp.height = ViewPager.LayoutParams.WRAP_CONTENT;
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(lp);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(resIds.get(position));
            ((ViewPager) view).addView(image, 0);
            return image;
        }

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object arg2) {
            ((ViewPager) view).removeView((View)arg2);
        }
    }

}
