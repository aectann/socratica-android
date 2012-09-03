package com.socratica.mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * This view hides it's contents if the text doesn't fit into the view vertically.
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 *
 */
public class AutoHideTextView extends TextView {

  Rect bounds = new Rect();

  public AutoHideTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int height = getHeight();
    if (getLineCount() * getLineHeight() <= height) {
      super.onDraw(canvas);      
    }
  }
}
