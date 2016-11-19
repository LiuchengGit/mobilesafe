/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Louris 201
 * 
 * 
 */
public class DragActivity extends Activity {

	private TextView tv_top;
	private TextView tv_bottom;
	private ImageView iv_drag;
	private int startX;
	private int startY;
	private int i = 0;
	private SharedPreferences msp;
	private int winWidth;
	private int winHeight;
	long[] mHits = new long[2];// 数组长度表示要点击的次数
	/*
	 * 复写父类方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag);
		msp = getSharedPreferences("config", MODE_PRIVATE);
		
		
		winWidth = getWindowManager().getDefaultDisplay().getWidth();
		winHeight = getWindowManager().getDefaultDisplay().getHeight();
		tv_top = (TextView) findViewById(R.id.tv_top);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		iv_drag = (ImageView) findViewById(R.id.iv_drag);
		int lastX = msp.getInt("lastX", 0);
		int lastY = msp.getInt("lastY", 0);
		if(lastY>winHeight/2){
			tv_top.setVisibility(View.VISIBLE);
			tv_bottom.setVisibility(View.INVISIBLE);
		}else{
			tv_top.setVisibility(View.INVISIBLE);
			tv_bottom.setVisibility(View.VISIBLE);
		}
		
        //获取布局对象
		RelativeLayout.LayoutParams params =(RelativeLayout.LayoutParams) iv_drag.getLayoutParams();
		
		params.leftMargin = lastX;
		params.topMargin=lastY;
		iv_drag.setLayoutParams(params);
		
		iv_drag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.debug("", "onClick");
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();// 开机后开始计算的时间
				if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
					// 把图片居中
					iv_drag.layout(winWidth / 2 - iv_drag.getWidth() / 2,
							iv_drag.getTop(), winWidth / 2 + iv_drag.getWidth()
									/ 2, iv_drag.getBottom());
					
					msp.edit().putInt("lastX", iv_drag.getLeft())
					.putInt("lastY", iv_drag.getTop()).commit();
				}
			}
		});
		iv_drag.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Utils.debug("", "onTouch");
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Utils.debug("", "***"+startX);
					Utils.debug("", "---"+event.getX());
					
					break;
				case MotionEvent.ACTION_MOVE:

					int endX = (int) event.getRawX();
					int endY = (int) event.getRawY();
					int dx = endX - startX;
					int dy = endY - startY;
					int l = iv_drag.getLeft() + dx;
					int t = iv_drag.getTop() + dy;
					int r = iv_drag.getRight() + dx;
					int b = iv_drag.getBottom() + dy;
					
					
					if(l<0 || r >winWidth || t<0 || b >winHeight -20)
					{
						break;
					}
					
					if(t>winHeight/2){
						tv_top.setVisibility(View.VISIBLE);
						tv_bottom.setVisibility(View.INVISIBLE);
					}else{
						tv_top.setVisibility(View.INVISIBLE);
						tv_bottom.setVisibility(View.VISIBLE);
					}
					
					iv_drag.layout(l, t, r, b);
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
//					i++;
//					Utils.debug("", "我正在移动" + i);

					break;
				case MotionEvent.ACTION_UP:
					
					msp.edit().putInt("lastX", iv_drag.getLeft())
							.putInt("lastY", iv_drag.getTop()).commit();
					break;
				default:
					break;
				}
				return false;
			}
			
		});
	}

//	/*
//	 * 复写父类方法
//	 */
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		
//		
//
//		//return super.onTouchEvent(event);
//		return false;
//	}
}
