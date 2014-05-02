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

import android.content.Context;
import android.graphics.Path;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 * 
 */
public interface MapParser {

  ArrayList<Area> parseAreas(Context context, int mapResource)
      throws XmlPullParserException, IOException;

  static final String ATTR_HREF = "href";
  static final String ATTR_COORDS = "coords";
  static final String AREA_TAG_NAME = "area";
  static final String ATTR_TARGET = "target";

  static class Area {
    public Path path;
    public String idStr;
    public int id;
    public int area;
    public int target;
  }
}
