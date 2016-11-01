OnScreenJoystick
================

On-screen joystick view for Android.

![](/screenshot.png "Screenshot from the example project.")

How to use
----------------
* Add the view just like any view via xml e.g.:
```    
<com.salamientertainment.view.onscreenjoystick.OnScreenJoystick
        android:id="@+id/directionJoystick"
        android:layout_width="200dp"
        android:layout_height="200dp" 
        android:layout_gravity="right|bottom"  
        android:background="@drawable/joystick_bg"/>
```
* Let your activity implement OnScreenJoystickListener interface:
```
public class MainActivity extends Activity implements OnScreenJoystickListener {
...
	@Override
	public void onTouch(OnScreenJoystick pJoystick, float pX, float pY) {
		Log.d("joy", "x: " + pX + " y: " + pY);

	}
...
}
```
* Add a listener to the joystick view:
```
((OnScreenJoystick) findViewById(R.id.directionJoystick))
				.setJoystickListener(this);
```
* Enjoy.
