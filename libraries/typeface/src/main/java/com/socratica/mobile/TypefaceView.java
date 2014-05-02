/*
 * Copyright (C) 2014 Socratica LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socratica.mobile;

import com.socratica.mobile.typeface.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Represents any view that supports text rendering with custom {@link android.graphics.Typeface}.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public interface TypefaceView {

  /**
   * Utility class to set custom font to a {@link com.socratica.mobile.TypefaceView}.
   *
   * @author Konstantin Burov (aectann@gmail.com)
   */
  static class TypefaceInitializer {
    /**
     * The method should be called by {@link com.socratica.mobile.TypefaceView} implementer constructor. It looks for a
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
