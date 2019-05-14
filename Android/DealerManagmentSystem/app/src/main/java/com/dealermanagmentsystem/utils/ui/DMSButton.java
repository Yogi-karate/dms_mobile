package com.dealermanagmentsystem.utils.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;



public class DMSButton extends AppCompatButton {
    public DMSButton(Context context) {
        super(context);
    }

    public DMSButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init();
    }

    public DMSButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init();
    }

    /**
     * This should be called after constructor, it if text of this widget is updated and automatically
     * load new text if it is
     */
    private void init() {
        setTypeface(DMSTypeFace.getTypeface(getContext()));
    }
}