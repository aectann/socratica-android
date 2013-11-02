package com.socratica.mobile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Just like image view, but with scrolling and scale abilities.
 * 
 * TODO write better description with usage examples.
 * 
 * @author Konstantin Burov (aectann@gmail.com)
 * 
 */
public class BigImage extends View implements OnGestureListener,
    OnTouchListener {

  private static final String ATTR_SRC = "src";
  private static final Map<String, SoftReference<Drawable>> DRAWABLE_CACHE = new HashMap<String, SoftReference<Drawable>>();

  protected float scale;
  protected float viewWidth;
  protected float viewHeight;
  protected float dx;
  protected float dy;
  protected Matrix matrix;
  protected boolean boundsInitialized;

  private GestureDetector gestureDetector;
  private float initScale;
  private int imageWidth;
  private int imageHeight;
  private int bitmapResource;
  private double scaleFactor;
  private String file;
  private int[] coords;

  public BigImage(Context context, AttributeSet attrs) {
    super(context, attrs);
    bitmapResource = attrs.getAttributeResourceValue(null, ATTR_SRC, 0);
    setFocusable(true);
    setFocusableInTouchMode(true);
    gestureDetector = new GestureDetector(context, this);
    this.setOnTouchListener(this);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (matrix != null) {
      int[] coords = getCoords();
      matrix.reset();
      ajustDeltas();
      matrix.postScale(scale, scale);
      matrix.postTranslate(dx + coords[0], dy + coords[1]);
      canvas.save();
      canvas.setMatrix(matrix);
      Drawable image = getImage();
      image.draw(canvas);
      canvas.restore();
    } else {
      super.onDraw(canvas);
    }
    if (!boundsInitialized) {
      initBounds();
    }
  }

  private int[] getCoords() {
    if (coords == null) {
      coords = new int[2];
      getLocationOnScreen(coords);
    }
    return coords;
  }

  private Drawable getImage() {
    String drawableKey = getDrawableKey();
    Drawable result = DRAWABLE_CACHE.containsKey(drawableKey) ? DRAWABLE_CACHE.get(drawableKey).get() : null;
    if (result == null) {
      if (bitmapResource > 0) {
        result = getResources().getDrawable(bitmapResource);
      } else {
        try {
          InputStream stream = new BufferedInputStream(
              new FileInputStream(file), 4096);
          Options options = new Options();
          options.inInputShareable = true;
          options.inPurgeable = true;
          options.inPreferredConfig = Config.RGB_565;
          options.inDither = true;
          result = new BitmapDrawable(getResources(), BitmapFactory.decodeStream(stream, null, options));
          stream.close();
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
      }
      result.setBounds(0, 0, imageWidth, imageHeight);
      DRAWABLE_CACHE.put(drawableKey, new SoftReference<Drawable>(result));
    }
    return result;
  }

  private String getDrawableKey() {
    return file == null ? String.valueOf(bitmapResource) : file;
  }

  protected synchronized void initBounds() {
    viewWidth = getMeasuredWidth();
    viewHeight = getMeasuredHeight();
    if (viewWidth > 0 && viewHeight > 0 && (bitmapResource > 0 || file != null)) {
      BitmapFactory.Options opt = loadBitmapOpts();
      imageWidth = opt.outWidth;
      imageHeight = opt.outHeight;
      initScale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
      dx = 0;
      dy = 0;
      matrix = new Matrix();
      scale = initScale;
      scaleFactor = 1 / initScale;
      this.boundsInitialized = true;
      notify();
    } else {
      matrix = null;
      this.boundsInitialized = false;
    }
    invalidate();
  }
  
  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    initBounds();
  }

  public void setImageFile(String file) {
    setImageFile(file, null);
  }

  public void setImageFile(String file, Drawable drawable) {
    this.file = file;
    this.bitmapResource = 0;
    if (drawable != null) {
      DRAWABLE_CACHE.put(getDrawableKey(),
          new SoftReference<Drawable>(drawable));
    }
    initBounds();
  }

  public void setImageResource(int drawable) {
    this.file = null;
    this.bitmapResource = drawable;
    initBounds();
  }

  private BitmapFactory.Options loadBitmapOpts() {
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inJustDecodeBounds = true;
    InputStream stream;
    if (bitmapResource > 0) {
      stream = getResources().openRawResource(bitmapResource);
    } else {
      try {
        stream = new BufferedInputStream(new FileInputStream(file));
      } catch (FileNotFoundException e) {
        throw new IllegalStateException(e);
      }
    }
    BitmapFactory.decodeStream(stream, null, opts);
    return opts;
  }

  private void ajustDeltas() {
    if (dx > 0) {
      dx = 0;
    }
    if (dy > 0) {
      dy = 0;
    }
    float minDx = 0;
    if (imageWidth * scale < viewWidth) {
      minDx = (viewWidth - imageWidth * scale) / 2;
    } else {
      minDx = -imageWidth * scale + viewWidth;
    }
    if (scale == initScale && viewWidth > viewHeight) {
      dx = (viewWidth - imageWidth * scale) / 2;
    } else if (dx < minDx) {
      dx = minDx;
    }
    float minDy = 0;
    if (imageHeight * scale < viewHeight) {
      minDy = (viewHeight - imageHeight * scale) / 2;
    } else {
      minDy = -imageHeight * scale + viewHeight;
    }
    if (scale == initScale && viewHeight > viewWidth) {
      dy = (viewHeight - imageHeight * scale) / 2;
    } else if (dy < minDy) {
      dy = minDy;
    }
  }

  /**
   * Restores the map state to the initial.
   */
  public void reset() {
    if (!boundsInitialized) {
      return;
    }
    scale = initScale;
    dx = 0;
    dy = 0;
    invalidate();
  }

  /**
   * Zooms map in, preserving currently centered point at the center of the
   * view.
   */
  public void scaleOut() {
    scale(1 / scaleFactor);
  }

  /**
   * Zooms map out, preserving currently centered point at the center of the
   * view.
   */
  public void scaleIn() {
    scale(scaleFactor);
  }

  protected void scale(double scaleFactor) {
    float prevDx = dx + (imageWidth * scale - viewWidth) / 2;
    prevDx *= scaleFactor;
    float prevDy = dy + (imageHeight * scale - viewHeight) / 2;
    prevDy *= scaleFactor;

    scale *= scaleFactor;

    if (scale < initScale) {
      scale = initScale;
    }

    dx = prevDx - (imageWidth * scale - viewWidth) / 2;
    dy = prevDy - (imageHeight * scale - viewHeight) / 2;
    invalidate();
  }

  @Override
  public boolean onDown(MotionEvent e) {
    return true;
  }

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
      float velocityY) {
    return true;
  }

  @Override
  public void onLongPress(MotionEvent e) {
  }

  @Override
  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    dx -= distanceX;
    dy -= distanceY;
    invalidate();
    return true;
  }

  @Override
  public void onShowPress(MotionEvent e) {
  }

  @Override
  public boolean onSingleTapUp(MotionEvent e) {

    if (scale < 0.7) {
      dx += viewWidth / 2 - e.getX();
      dy += viewHeight / 2 - e.getY();
      scaleIn();
      return true;
    }
    return false;
  }

  double prevDelta = 0;

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    if (event.getPointerCount() == 1) {
      gestureDetector.onTouchEvent(event);
    } else {
      float x = event.getX(0);
      float y = event.getY(0);
      float prevX = event.getX(1);
      float prevY = event.getY(1);

      int action = event.getAction();
      int actionCode = action & MotionEvent.ACTION_MASK;
      if (actionCode == MotionEvent.ACTION_DOWN
          || actionCode == MotionEvent.ACTION_POINTER_DOWN) {
        prevDelta = 0;
        dx += viewWidth / 2 - (x + prevX) / 2;
        dy += viewHeight / 2 - (y + prevY) / 2;
      } else {
        double currentDelta = getDelta(x, y, prevX, prevY);
        if (prevDelta != 0) {
          double dd = currentDelta - prevDelta;
          float abs = (float) Math.abs(dd);
          if (abs > 2) {
            double scaleFactor = scale * (1 + (float) dd / 200);
            scaleFactor = scaleFactor / scale;
            scale(scaleFactor);
          }
        }
        prevDelta = currentDelta;
      }
      invalidate();
    }
    return true;
  }

  protected double getDelta(float x, float y, float prevX, float prevY) {
    return Math.sqrt((x - prevX) * (x - prevX) + (y - prevY) * (y - prevY));
  }

}