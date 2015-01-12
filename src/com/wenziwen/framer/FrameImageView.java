package com.wenziwen.framer;

import com.wenziwen.framer.Frame.Target;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

public class FrameImageView extends ImageView {
	private Target[] mTargets = null;
	private Bitmap mBitmap;
	public FrameImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mTargets != null) {
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE);
			for (Target target : mTargets) {
				canvas.drawRect(target.left, target.top, target.left + target.width,
						target.top + target.height, paint);
			}
		}
	}
	
	public void setTargets(Target[] targets) {
		mTargets = targets;
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		mBitmap = bm;
		super.setImageBitmap(bm);
	}
	
	
}
