package com.codepath.wangela.apps.rocklizardspock.models;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SlidingRelativeLayout extends RelativeLayout {
	private WindowManager wm; 
	private Display display;
	private Point size = new Point();

	public SlidingRelativeLayout(Context context) {
		super(context);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
	}

	public SlidingRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();

	}

	public SlidingRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();

	}
	
    public float getXFraction()
    {
    	display.getSize(size);
        int width = size.x;
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction) {
    	display.getSize(size);
        int width = size.x;
        setX((width > 0) ? (xFraction * width) : 0);
    }
}
