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
import android.widget.TextView;

/**
 * This view hides it's contents if the text doesn't fit into the view
 * vertically.
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 * 
 */
public class AutoHideTextView extends TextView {

  public AutoHideTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onPreDraw() {
    int visibility = getVisibility();
    if (getLineCount() * getLineHeight() <= getHeight()) {
      if (GONE != visibility) {
        setVisibility(GONE);
        requestLayout();
      }
    } else {
      if (VISIBLE != visibility) {
        setVisibility(VISIBLE);
        requestLayout();
      }
    }
    return super.onPreDraw();
  }
}
