package com.bookservice.utils.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by nikhil.v on 6/16/2017.
 */

public class BsTextView extends AppCompatTextView {

    public BsTextView(Context context) {
        super(context);
        if (!isInEditMode())
            init();
    }

    public BsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init();
    }

    public BsTextView(Context context, AttributeSet attrs, int defStyle) {
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
