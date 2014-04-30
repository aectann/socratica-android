package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * A view that hides itself if it's content doesn't fit vertically.
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 *
 */
public class AutohideTypefaceTextView extends TypefaceTextView {

  public AutohideTypefaceTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  
  @Override
  public boolean onPreDraw() {
    int visibility = getVisibility();
    if (getLineCount() * getLineHeight() > getHeight()) {
      if (visibility != View.GONE) {
        setVisibility(View.GONE);
        requestLayout();
      }
    } else {
      if (visibility != View.VISIBLE) {
        setVisibility(View.VISIBLE);
        requestLayout();
      }
    }
    return super.onPreDraw();
  }
}
