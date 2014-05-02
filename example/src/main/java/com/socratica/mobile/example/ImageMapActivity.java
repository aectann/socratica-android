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
package com.socratica.mobile.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.socratica.mobile.ImageMap;
import com.socratica.mobile.imagemap.ImageMapListener;


public class ImageMapActivity extends ActionBarActivity {

  private ImageMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_map);
    map = (ImageMap) findViewById(R.id.map);
    map.setImageMapListener(new ImageMapListener() {
      @Override
      public void onAreaClicked(int areaId) {
        map.showArea(areaId);
        Toast.makeText(ImageMapActivity.this, "You've clicked on an active area!", Toast.LENGTH_SHORT).show();
      }
    });
  }

}
