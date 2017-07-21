package com.zss.library.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import com.zss.library.R;
import com.zss.library.activity.BrowserActivity;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 * @author zm
 */
public class BrowserPagerAdapter extends PagerAdapter {
    private Context context;
    private List<?> imageSrc;
    private int loadType;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;


    public BrowserPagerAdapter(Context context, List<?> imageSrc, int loadType) {
        this.context = context;
        this.imageSrc = imageSrc;
        this.loadType = loadType;
        cacheView = new SparseArray<>(imageSrc.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if(containerTemp == null) containerTemp = container;
        View view = cacheView.get(position);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_vp_image,container,false);
            view.setTag(position);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            if(loadType == BrowserActivity.URL){
                String url = (String)imageSrc.get(position);
                Glide.with(context).load(url).asBitmap().into(new MyTarget(photoViewAttacher));
            } else if(loadType == BrowserActivity.RES){
                Integer res = (Integer) imageSrc.get(position);
                Glide.with(context).load(res).asBitmap().into(new MyTarget(photoViewAttacher));
            }else if(loadType == BrowserActivity.FILE){
                String src = (String)imageSrc.get(position);
                File file = new File(src);
                Glide.with(context).load(file).asBitmap().into(new MyTarget(photoViewAttacher));
            }
            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imageSrc.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private class MyTarget extends SimpleTarget<Bitmap> {

        private PhotoViewAttacher viewAttacher;

        public MyTarget(PhotoViewAttacher viewAttacher){
            this.viewAttacher = viewAttacher;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            viewAttacher.getImageView().setImageBitmap(resource);
            viewAttacher.update();
        }
    }
}
