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
package com.socratica.mobile.imagemap;

import android.graphics.Paint.Style;

/**
 * @author aectann@gmail.com (Konstantin Burov)
 */
public class PaintType {

  public Style style;
  public int color;

  public PaintType(Style style, int color) {
    super();
    this.style = style;
    this.color = color;
  }
}
