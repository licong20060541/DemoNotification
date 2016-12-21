package com.raintail.demonotification;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by licong10 on 16-8-18.
 */
public class ClipButton extends Button {

    public ClipButton(Context context) {
        super(context);
    }

    public ClipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        Log.i("ClipButton", outRect.toString());
//        outRect.left += getTranslationX();
//        outRect.right += getTranslationX();
//        outRect.bottom = (int) (outRect.top + getTranslationY() + getActualHeight());
//        outRect.top += getTranslationY() + getClipTopAmount();
    }

}
