package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * This view hides it's contents if the text doesn't fit into the view vertically.
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 *
 */
public class AutoHideTextView extends TextView {

  public AutoHideTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  
  @Override
  public boolean onPreDraw() {
    int visibility = getVisibility();
    if (getLineCount() * getLineHeight() <= getHeight()) {
      if (GONE != visibility) {
        setVisibility(GONE);
        requestLayout();
      }
    } else {
      if (VISIBLE != visibility) {
        setVisibility(VISIBLE);
        requestLayout();
      }
    }
    return super.onPreDraw();
  }
}
