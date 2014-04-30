package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.socratica.mobile.TypefaceView.TypefaceInitializer;

/**
 * Regular {@link android.widget.Button} that just allows usage of 'customTypeface' attribute to specify
 * font for text rendering. See {@link com.socratica.mobile.TypefaceView.TypefaceInitializer} for details.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public class TypefaceButton extends Button implements TypefaceView {

  public TypefaceButton(Context context) {
    super(context);
    TypefaceInitializer.init(this, context, null);
  }
  
  public TypefaceButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceInitializer.init(this, context, attrs);
  }
  
  public TypefaceButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    TypefaceInitializer.init(this, context, attrs);
  }
  
  

}
