package com.socratica.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;

/**
 * Just like image view, but with scrolling and scale abilities.
 * 
 * TODO write better description with usage examples.
 * 
 * @author Konstantin Burov (aectann@gmail.com)
 *
 */
public class BigImage extends View implements OnGestureListener, OnTouchListener  {

	/**
	 * Simplest cache to remove bitmap loading overhead. Uses weak references to prevent OutOfMemory errors. 
	 */
	private static final Map<String, WeakReference<Bitmap>> bitmapsCache = new HashMap<String, WeakReference<Bitmap>>();
	
	
	/**
	 * TODO
	 */
	protected static final String ATTR_BOUND_PAD = "boundPad";
	protected static final double DEFAULT_SCALE_FACTOR = 2.56;
	protected static final String ATTR_MAP = "map";
	protected static final String ATTR_SRC = "src";
	protected float scale;
	protected float viewWidth;
	protected float viewHeight;
	protected GestureDetector gestureDetector;
	protected float dx;
	protected float dy;
	protected Matrix matrix;
	protected RectF bounds;
	protected float initScale;
	private int imageWidth;
	private int imageHeight;
	protected boolean boundsInitialized;
	protected Handler guiHander;
	protected int bitmapResource;
	private String file;
	private Paint paint;

	public BigImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		guiHander = new Handler();
		bitmapResource = attrs.getAttributeResourceValue(null, ATTR_SRC, 0);
		setFocusable(true);
		setFocusableInTouchMode(true);
		gestureDetector = new GestureDetector(context, this);
		this.setOnTouchListener(this);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (matrix != null) {
			matrix.reset();
			ajustDeltas();
			matrix.postScale(scale, scale);
			matrix.postTranslate(dx, dy);
			canvas.drawBitmap(getImage(), matrix, paint);
			canvas.save(Canvas.CLIP_TO_LAYER_SAVE_FLAG);
		} else {
			super.onDraw(canvas);
		}
		if(!boundsInitialized){
			initBounds();
		}
	}

	private Bitmap getImage() {
		Bitmap bitmap = null;
		String bitmapKey = null;
		
		if(bitmapResource > 0){
			bitmapKey = String.valueOf(bitmapResource);
		} else{
			bitmapKey = this.file;
		}
		
		if(bitmapsCache.containsKey(bitmapKey)){
			WeakReference<Bitmap> weakReference = bitmapsCache.get(bitmapKey);
			bitmap = weakReference.get();
		}
		
		if(bitmap == null){
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			bitmap = decodeBitmap(opts);
			bitmapsCache.put(bitmapKey, new WeakReference<Bitmap>(bitmap));
		}
		return bitmap;
	}

	protected synchronized void initBounds() {
			viewWidth = getMeasuredWidth();
			viewHeight = getMeasuredHeight();
			if(viewWidth > 0 && viewHeight > 0){
				BitmapFactory.Options opt = loadBitmapOpts();
				imageWidth = opt.outWidth;
				imageHeight = opt.outHeight;
				initScale = Math.min(viewWidth/imageWidth, viewHeight/imageHeight);
				dx = 0;
				dy = 0;
				matrix = new Matrix();
				scale = initScale;
				this.boundsInitialized = true;
				notify();
			} else {
				matrix = null;
				this.boundsInitialized = false;
			}
			invalidate();
		}
	
	public void setImageFile(String file){
		this.file = file;
		this.bitmapResource = 0;
		initBounds();
	}

	private BitmapFactory.Options loadBitmapOpts() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		decodeBitmap(opts);
		return opts;
	}

	protected Bitmap decodeBitmap(BitmapFactory.Options opts) {
		Bitmap bitmap;
		InputStream stream;
		if(bitmapResource > 0){
			stream = getResources().openRawResource(bitmapResource);
		} else{
			try {
				stream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
		bitmap = BitmapFactory.decodeStream(stream ,null, opts);
		return bitmap;
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
		scale(1 / DEFAULT_SCALE_FACTOR);
	}

	/**
	 * Zooms map out, preserving currently centered point at the center of the
	 * view.
	 */
	public void scaleIn() {
		scale(DEFAULT_SCALE_FACTOR);
	}

	protected void scale(double scaleFactor) {
		float prevDx = dx + (imageWidth * scale - viewWidth) / 2;
		prevDx *= scaleFactor;
		float prevDy = dy + (imageHeight * scale - viewHeight) / 2;
		prevDy *= scaleFactor;

		scale *= scaleFactor;
		
		if(scale < initScale){
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
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
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
		if(scale <= initScale * DEFAULT_SCALE_FACTOR){
			dx += viewWidth/2 - e.getX();
			dy += viewHeight/2 - e.getY();
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
					|| actionCode == MotionEvent.ACTION_POINTER_DOWN){
				prevDelta = 0;
				dx += viewWidth/2 - (x + prevX) / 2;
				dy += viewHeight/2 - (y + prevY) / 2;
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
		return Math.sqrt((x - prevX) * (x- prevX) + (y - prevY) * (y - prevY));
	}

}