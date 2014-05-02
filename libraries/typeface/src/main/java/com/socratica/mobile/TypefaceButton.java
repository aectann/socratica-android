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
