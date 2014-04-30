package com.socratica.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

import com.socratica.mobile.TypefaceView.TypefaceInitializer;

/**
 * Regular {@link android.widget.Chronometer} that just allows usage of 'customTypeface' attribute to specify
 * font for text rendering. See {@link com.socratica.mobile.TypefaceView.TypefaceInitializer} for details.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public class TypefaceChronometer extends Chronometer implements TypefaceView {

  public TypefaceChronometer(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceInitializer.init(this, context, attrs);
  }

}
