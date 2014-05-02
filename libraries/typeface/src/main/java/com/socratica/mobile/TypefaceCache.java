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

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.socratica.mobile.typeface.R;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;

/**
 * Utility class to remove overhead of loading same typeface several times.
 *
 * @author Konstantin Burov (aectann@gmail.com)
 */
public final class TypefaceCache {

  private static TypefaceCache INSTANCE;

  private final Map<String, WeakReference<Typeface>> typeFaceCache =
      new HashMap<String, WeakReference<Typeface>>();

  public final Typeface getTypeFace(String typeFaceFileName, Resources res) {
    Typeface myTypeface = null;
    if (typeFaceCache.containsKey(typeFaceFileName)) {
      myTypeface = typeFaceCache.get(typeFaceFileName).get();
    }
    if (myTypeface == null) {
      try {
        myTypeface = Typeface.createFromAsset(res.getAssets(), typeFaceFileName);
        typeFaceCache.put(typeFaceFileName, new WeakReference<Typeface>(myTypeface));
      } catch (Exception e) {
        // Something weird happened.. just fallback to default typeface.
      }
    }
    return myTypeface;
  }

  public final Typeface getDefaultTypeFace(Resources res) {
    TypedArray a = res.obtainTypedArray(R.style.TypefaceView);
    String typeface = a.getString(R.styleable.TypeFaceView_customTypeface);
    a.recycle();
    return getTypeFace(typeface, res);
  }

  public static final TypefaceCache getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TypefaceCache();
    }
    return INSTANCE;
  }
}
