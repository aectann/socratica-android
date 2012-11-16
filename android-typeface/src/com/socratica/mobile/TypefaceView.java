package com.socratica.mobile;

import com.socratica.mobile.typeface.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Represents any view that supports text rendering with custom {@link Typeface}.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public interface TypefaceView {

  /**
   * Utility class to set custom font to a {@link TypefaceView}.
   *
   * @author Konstantin Burov (aectann@gmail.com)
   */
  static class TypefaceInitializer {
    /**
     * The method should be called by {@link TypefaceView} implementer constructor. It looks for a
     * 'customTypeface' attribute on view's xml declaration. If the attribute not found on view's
     * xml declaration the value defaults are extracted from style specified by 'typefaceViewStyle'
     * attribute on current theme. If the theme doesn't specify the 'typefaceViewStyle' attribute,
     * the value extracted from 'TypefaceView' style.
     *
     * Value of the 'customTypeface' attribute must correspond to filename of a font in '/assets'
     * folder.
     *
     * For example if you have font at '/assets/Times.ttf' the customTypeface value would be
     * 'Times.ttf'.
     *
     * @param view - a view that wants to use custom font for rendering.
     * @param context - views context
     * @param attrs - attributes applied to the view.
     */
    static void init(TypefaceView view, Context context, AttributeSet attrs) {
      if (!view.isInEditMode()) {
        TypedArray a =
            context.obtainStyledAttributes(attrs, R.styleable.TypeFaceView,
                R.attr.typefaceViewStyle, R.style.TypefaceView);
        String typeFaceName = a.getString(R.styleable.TypeFaceView_customTypeface);
        a.recycle();
        if (typeFaceName != null) {
          Typeface typeFace =
              TypefaceCache.getInstance().getTypeFace(typeFaceName, context.getResources());
          if (typeFace != null) view.setTypeface(typeFace);
        }
      }
    }
  }

  void setTypeface(Typeface typeface);

  boolean isInEditMode();
}
