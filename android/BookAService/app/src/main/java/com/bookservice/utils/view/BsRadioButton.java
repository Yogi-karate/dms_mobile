package com.bookservice.utils.view;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class BsRadioButton extends AppCompatRadioButton {

    public BsRadioButton(Context context) {
        super(context);
        if (!isInEditMode())
            init();
    }

    public BsRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init();
    }

    public BsRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init();
    }

    /**
     * This should be called after constructor, it if text of this widget is updated and automatically
     * load new text if it is
     */
    private void init() {
        setTypeface(BsTypeFace.getTypeface(getContext()));
    }
}


