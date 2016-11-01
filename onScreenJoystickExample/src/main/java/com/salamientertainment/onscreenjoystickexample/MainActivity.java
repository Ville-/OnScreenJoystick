

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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.salamientertainment.view.onscreenjoystick.OnScreenJoystick;
import com.salamientertainment.view.onscreenjoystick.OnScreenJoystickListener;

public class MainActivity extends Activity implements OnScreenJoystickListener {

	private final int VELOCITY = 3;

	private TestSurface mTestSurface;
	private TextView mJoystickValueTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI() {
		((OnScreenJoystick) findViewById(R.id.directionJoystick))
				.setJoystickListener(this);
		mJoystickValueTextView = (TextView) findViewById(R.id.joystick_value_textview);
		mTestSurface = (TestSurface) findViewById(R.id.surface);
	}

	@Override
	public void onTouch(OnScreenJoystick pJoystick, float pX, float pY) {
		mTestSurface.move(pX * VELOCITY, -pY * VELOCITY);
		mJoystickValueTextView.setText("Joystick: " + pX + ", " + pY);

	}

}
