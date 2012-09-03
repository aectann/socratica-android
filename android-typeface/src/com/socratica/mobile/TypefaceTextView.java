package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Regular {@link TextView} that just allows usage of 'customTypeface' attribute to specify
 * font for text rendering. See {@link TypefaceInitializer} for details.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public class TypefaceTextView extends TextView implements TypefaceView {

  public TypefaceTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceInitializer.init(this, context, attrs);
  }
  
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
