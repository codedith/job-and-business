package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroScreenViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int[] introSliderLayouts;

    public IntroScreenViewPagerAdapter(Context context, int[] introSliderLayouts) {
        this.context = context;
        this.introSliderLayouts = introSliderLayouts;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(introSliderLayouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return introSliderLayouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}