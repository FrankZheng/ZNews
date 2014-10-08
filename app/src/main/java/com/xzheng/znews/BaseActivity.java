package com.xzheng.znews;


import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.xzheng.znews.util.BackGestureListener;

public class BaseActivity extends Activity {

	GestureDetector mGestureDetector;
	private boolean mNeedBackGesture = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initGestureDetector();
	}

	private void initGestureDetector() {
		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getApplicationContext(),
					new BackGestureListener(this));
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(mNeedBackGesture){
			return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}
	
	/*
	 * 
	 */
	public void setNeedBackGesture(boolean mNeedBackGesture){
		this.mNeedBackGesture = mNeedBackGesture;
	}
	
	/*
	 * 
	 * 	 
	 */ 
	public void doBack(View view) {
		onBackPressed();
	}

}
