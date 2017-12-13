package com.geliddroid.mysore.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by levin on 06-12-2017.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    public CustomButton(Context context) {
        super(context);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myfont = Typeface.createFromAsset(context.getAssets(), "fonts/baarpb__.TTF");
        this.setTypeface(myfont);
    }
}
