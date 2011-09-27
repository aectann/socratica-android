package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.socratica.mobile.TypefaceView.TypefaceInitializer;

/**
 * Regular {@link Button} that just allows usage of 'customTypeface' attribute to specify
 * font for text rendering. See {@link TypefaceInitializer} for details.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public class TypefaceButton extends Button implements TypefaceView {

  public TypefaceButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceInitializer.init(this, context, attrs);
  }

}
