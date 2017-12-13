package com.geliddroid.mysore.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by levin on 06-12-2017.
 */

public class CustomTextViews extends AppCompatTextView {
    public CustomTextViews(Context context) {
        super(context);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/sophia_normal.TTF");
        this.setTypeface(myfont);
    }

    public CustomTextViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/sophia_normal.TTF");
        this.setTypeface(myfont);
    }

    public CustomTextViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/sophia_normal.TTF");
        this.setTypeface(myfont);
    }
}
