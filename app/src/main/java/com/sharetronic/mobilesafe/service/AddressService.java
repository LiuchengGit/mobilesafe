/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.service;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.Utils;

import CityAccessDao.CityAccessDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author Louris 201
 * 
 * 
 */
public class AddressService extends Service {

	private TelephonyManager telephonyManager;
	private Mylistener listener;
	private OutCallReceiver receiver;
	private WindowManager mWM;
	private View view;
	private SharedPreferences msp;

	/*
	 * 复写父类方法
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * 复写父类方法
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		msp = getSharedPreferences("config", MODE_PRIVATE);
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		listener = new Mylistener();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE); // 监听电话铃响
		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(receiver, filter);

	}

	public class Mylistener extends PhoneStateListener {

		/*
		 * 复写父类方法
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING: // 监听到了来电
				Utils.debug("", "电话铃响了");
				String city = CityAccessDao.getCity(incomingNumber);
				// Utils.showLongToast(AddressService.this,city );
				showFloatToast(city);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if (mWM != null && view != null) {
					mWM.removeView(view);
					view = null;
				}
				break;
			default:
				break;
			}

		}

	}

	public class OutCallReceiver extends BroadcastReceiver {

		/*
		 * 复写父类方法 android.permission.PROCESS_OUTGOING_CALLS监听去电广播需要此权限
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			String number = getResultData();
			String city = CityAccessDao.getCity(number);
			// Utils.showLongToast(context,city );
			showFloatToast(city);
		}

	}

	/*
	 * 复写父类方法
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		unregisterReceiver(receiver);
	}

	public void showFloatToast(String text) {
		
		mWM = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		final int width = mWM.getDefaultDisplay().getWidth();
		final int Height= mWM.getDefaultDisplay().getHeight();
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		params.gravity = Gravity.LEFT + Gravity.TOP;
		int[] pic = new int[] { R.drawable.call_locate_orange,
				R.drawable.call_locate_gray, R.drawable.call_locate_green,
				R.drawable.call_locate_white, R.drawable.call_locate_blue };
		
        int style= msp.getInt("addressstyle", 0);
        
        int lastX = msp.getInt("lastX",0);
        int lastY = msp.getInt("lastY", 0);
        params.x = lastX;
        params.y = lastY;
        
		view = View.inflate(this, R.layout.address_background, null);
		
		view.setOnTouchListener(new OnTouchListener() {
			
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Utils.debug("", "正在触摸");
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					//Utils.debug("", "***"+startX);
					//Utils.debug("", "---"+event.getX());
					
					break;
				case MotionEvent.ACTION_MOVE:

					int endX = (int) event.getRawX();
					int endY = (int) event.getRawY();
					int dx = endX - startX;
					int dy = endY - startY;
					
					  params.x += dx;
				      params.y +=dy;
					
				      if(params.x<0)
				      {
				    	  params.x=0;
				      }
				      if(params.y<0)
				      {
				    	  params.y=0;
				      }
				      if(params.x> width-view.getWidth())
				      {
				    	  params.x=width-view.getWidth();
				      }
				      if(params.y>Height-view.getHeight())
				      {
				    	  params.y=Height-view.getHeight();
				      }
				     mWM.updateViewLayout(view, params);
					 startX = (int) event.getRawX();
					 startY = (int) event.getRawY();
                     

					break;
				case MotionEvent.ACTION_UP:
					
					msp.edit().putInt("lastX", params.x)
							.putInt("lastY", params.y).commit();
					break;
				default:
					break;
				}
				return false;
			}
		});
		
		view.setBackgroundResource(pic[style]);
		
		TextView textview = (TextView) view.findViewById(R.id.tv_address);
		textview.setText(text);
		mWM.addView(view, params);

	}
}
