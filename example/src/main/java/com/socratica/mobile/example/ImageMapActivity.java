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
