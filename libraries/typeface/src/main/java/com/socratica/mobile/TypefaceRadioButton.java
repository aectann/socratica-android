package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.socratica.mobile.TypefaceView.TypefaceInitializer;

/**
 * Regular {@link android.widget.RadioButton} that just allows usage of 'customTypeface' attribute to specify
 * font for text rendering. See {@link com.socratica.mobile.TypefaceView.TypefaceInitializer} for details.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public class TypefaceRadioButton extends RadioButton implements TypefaceView {

  public TypefaceRadioButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceInitializer.init(this, context, attrs);
  }

}
