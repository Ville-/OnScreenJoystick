package com.salamientertainment.onscreenjoystickexample;

/*
 * Copyright (c) 2014 Ville Saarinen

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestSurface extends SurfaceView implements
SurfaceHolder.Callback{

	private SurfaceHolder mHolder;
	private float mXCoordinate, mYCoordinate;
	private Bitmap mBitmap;
	private Matrix mMatrix;
	private MyThread mThread;
	private float mVelocityY;
	private float mVelocityX;
	
	public TestSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {

		mBitmap = BitmapFactory
				.decodeResource(
						getResources(),
						com.salamientertainment.onscreenjoystickexample.R.drawable.ic_launcher);
		mThread = new MyThread();
		mHolder = getHolder();
		mHolder.addCallback(this);
		
		mXCoordinate = 200;
		mYCoordinate = 200;
		mMatrix = new Matrix();
		mMatrix.setTranslate(mXCoordinate, mYCoordinate);
		setZOrderOnTop(true); 
		mHolder.setFormat(PixelFormat.TRANSPARENT);

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		mThread.setRunning(false);

		while (retry) {
			try {
				// code to kill Thread
				mThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		
	}
	
	private void moveBy(final float pX, final float pY){
		if(pX != 0 || pY != 0 ){
		mXCoordinate += pX;
		mYCoordinate += pY;
		this.mMatrix.reset();
		mMatrix.setTranslate(mXCoordinate, mYCoordinate);
		}
	}
	
	public void move(final float pX, final float pY){
		mVelocityX = pX;
		mVelocityY = pY;
	}
	
	private void resetCanvas(Canvas canvas){
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}


	public void doDraw(final Canvas pCanvas) {
		pCanvas.drawBitmap(mBitmap, mMatrix, null);
	}
	
	private class MyThread extends Thread {

		private boolean running = false;

		@Override
		public synchronized void start() {
			running = true;
			super.start();
		}

		public void setRunning(final boolean pRunning) {
			running = pRunning;
		}

		@Override
		public void run() {
			while (running) {
				moveBy(mVelocityX, mVelocityY);
				Canvas canvas = null;
				try {
					canvas = mHolder.lockCanvas(null);
					synchronized (mHolder) {
						resetCanvas(canvas);
						doDraw(canvas);
					}
				} 
				catch(Exception e){}
				finally {
					if (canvas != null) {
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
}
