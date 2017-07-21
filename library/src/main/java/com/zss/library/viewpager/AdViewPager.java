package com.zss.library.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import com.zss.library.R;
import com.zss.library.indicator.CirclePageIndicator;

import java.util.List;

/**
 * Created by zm on 2017/3/8.
 */

public class AdViewPager extends RelativeLayout {

    private RelativeLayout rootView;
    private CycleViewPager viewPager;
    private CyclePageIndicator indicator;
    private List<String> urls;
    private List<Integer> resIds;

    public AdViewPager(Context context) {
        super(context);
        initView();
    }

    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AdViewPager(Context context, AttributeSet attrs, int defStyleAtrr) {
        super(context, attrs, defStyleAtrr);
        initView();
    }

    public void initView(){
        inflate(getContext(), R.layout.viewpager_ad, this);
        rootView = (RelativeLayout) findViewById(R.id.common_rl_root);
        viewPager = (CycleViewPager) findViewById(R.id.viewPager);
        indicator = (CyclePageIndicator)findViewById(R.id.indicator);
    }

    public void setHeight(int px) {
        LayoutParams lp = (LayoutParams) rootView.getLayoutParams();
        lp.height = px;
        rootView.setLayoutParams(lp);
    }

    public void setUrls(List<String> urls){
        this.urls = urls;
        AdPagerAdapter adapter = new AdPagerAdapter();
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    public void setResIds(List<Integer> localResIds){
        this.resIds = localResIds;
        AdPagerAdapter adapter = new AdPagerAdapter();
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    public CycleViewPager getViewPager() {
        return viewPager;
    }

    class AdPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if(urls == null && resIds == null){
                return 0;
            }
            if (urls != null) {
                return urls.size();
            } else {
                return resIds.size();
            }
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
            lp.width = ViewPager.LayoutParams.MATCH_PARENT;
            lp.height = ViewPager.LayoutParams.WRAP_CONTENT;
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(lp);
            if(urls!=null){
                Glide.with(getContext()).load(urls.get(position)).placeholder(R.mipmap.ic_photo_loading).into(image);
            }else{
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setImageResource(resIds.get(position));
            }
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
