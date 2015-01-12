package com.wenziwen.framer;

import java.io.FileInputStream;
import java.util.List;

import com.wenziwen.framer.Frame.Target;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private String TAG = "MainActivity";
	private List<Frame> mFrameList;
	private FrameImageView mImageView;
	private int mCurrentIndex = 0;
	private int SCREEN_WIDTH = 0;
	private int SCREEN_HEIGHT = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
		SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight();
		
		mImageView = (FrameImageView) findViewById(R.id.imageView);
		mImageView.setOnTouchListener(mOnTouchListener);
		parseXml();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void parseXml() {
		try {
			String info = new String();
			mFrameList = FrameParser.parse(new FileInputStream("sdcard/frame/frame.txt"));
			for(Frame frame : mFrameList) {
				info = info + frame.toString();
				Log.d(getPackageName(), frame.toString());
			}

			// ÇÐ»»Í¼Æ¬
			changeView();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				float posX = event.getX();
				float posY = event.getY();
				for (Target target : mFrameList.get(mCurrentIndex).targets) {
					if (isPointInTarget(posX, posY, target)) {
						mCurrentIndex  = target.target - 1;
						changeView();
					}
				}
			}			
			return false;
		}

	};
	
	
	
	private void changeView() {
		Log.d(TAG, "change bitmap: index = " + mCurrentIndex);
		Bitmap bm = BitmapFactory.decodeFile("/sdcard/frame/" + mFrameList.get(mCurrentIndex).fileName + ".png");
		Target[] array = new Target[mFrameList.get(mCurrentIndex).targets.size()];
		
		if (mFrameList.get(mCurrentIndex).targets != null) {
			for (int i=0; i<mFrameList.get(mCurrentIndex).targets.size(); i++) {
				mFrameList.get(mCurrentIndex).targets.get(i).left = getNewX(mFrameList.get(mCurrentIndex).targets.get(i).left, bm);
				mFrameList.get(mCurrentIndex).targets.get(i).top = getNewY(mFrameList.get(mCurrentIndex).targets.get(i).top, bm);
				mFrameList.get(mCurrentIndex).targets.get(i).width = getNewX(mFrameList.get(mCurrentIndex).targets.get(i).width, bm);
				mFrameList.get(mCurrentIndex).targets.get(i).height = getNewY(mFrameList.get(mCurrentIndex).targets.get(i).height, bm);
			}
		}
		
		mImageView.setImageBitmap(bm);
		mImageView.setTargets(mFrameList.get(mCurrentIndex).targets.toArray(array));
	}
	
	private boolean isPointInTarget(float posX, float posY, Target target) {
		if(posX > target.left && posX < (target.left + target.width)
				&& posY > target.top && posY < (target.top + target.height)) {
			return true;
		}
		return false;
	}
	
	private int getNewX(int oldX, Bitmap bm) {
		return oldX * SCREEN_WIDTH / bm.getWidth();
	}
	
	private int getNewY(int oldY, Bitmap bm) {
		return oldY * SCREEN_HEIGHT  / bm.getHeight();
	}
	
}
