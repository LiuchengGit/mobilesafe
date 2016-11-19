/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Louris 2015
 *
 * 如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：2.
 */
public abstract class BaseActivity extends Activity {
	private GestureDetector mdetector;
	public SharedPreferences msp;
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		msp = getSharedPreferences("config", MODE_PRIVATE);
	     mdetector = new GestureDetector(BaseActivity.this, new  GestureDetector.SimpleOnGestureListener(){
	    	 
	    	 /* 复写父类方法
	    	 * 
	    	 */
	    	@Override
	    	public boolean onFling(MotionEvent e1, MotionEvent e2,
	    			float velocityX, float velocityY) { 
	    		if(Math.abs(e2.getRawY()-e1.getRawY() )>200)
	    		{
	    			Toast.makeText( BaseActivity.this, "不能这样滑动",Toast.LENGTH_SHORT).show();
	    			return true;
	    		}
	    		if(Math.abs(velocityX)<150)
	    		{
	    			Toast.makeText( BaseActivity.this, "滑动太慢",Toast.LENGTH_SHORT).show();
	    			return true;
	    		}
	    	  //下一页
	    		if(e1.getRawX()-e2.getRawX() >200){
	    			
	    			 showNextPage();
	    		}
	    		//上一页
	    		if(e2.getRawX()-e1.getRawX() >200){
	    			
	    			showPreviousPage();
	    		}
	    		
	    		
	    		return super.onFling(e1, e2, velocityX, velocityY);
	    	}

			
	     });
 }
	
	public abstract void  showPreviousPage();
	public abstract void showNextPage();
	 
	public void next(View view){
		showNextPage();
	}
	public void previous(View view){
		showPreviousPage();
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mdetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}