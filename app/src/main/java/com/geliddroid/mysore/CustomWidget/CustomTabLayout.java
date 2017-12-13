package com.geliddroid.mysore.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by levin on 30-11-2017.
 */

public class CustomTabLayout extends TabLayout {

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        this.removeAllTabs();
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/baarpb__.TTF");
        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
        PagerAdapter adapter = viewPager.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            Tab tab = this.newTab();
            this.addTab(tab.setText(adapter.getPageTitle(i)));
            AppCompatTextView view = (AppCompatTextView) ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
            view.setTypeface(typeface, Typeface.BOLD);
        }
    }
}