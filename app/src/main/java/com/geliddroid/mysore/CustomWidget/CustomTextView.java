package com.geliddroid.mysore.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by levin on 04-12-2017.
 */

public class CustomTextView extends AppCompatTextView{
    public CustomTextView(Context context) {
        super(context);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }
}
