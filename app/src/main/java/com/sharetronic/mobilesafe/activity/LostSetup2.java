/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.Utils;
import com.sharetronic.mobilesafetview.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Louris 2015
 *
 * 如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：2.
 */
public class LostSetup2 extends BaseActivity  {
	private  SettingItemView  siv_sim;
	private GestureDetector mdetector;
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     setContentView(R.layout.lost__setup2);
	     siv_sim = (SettingItemView) findViewById(R.id.siv_sim);
	     String sim = msp.getString("sim", null);
	     if(!TextUtils.isEmpty(sim))
	     {
	    	 siv_sim.setChecked(true);
	     }else{
	    	
	    	 siv_sim.setChecked(false);
	     }
	     siv_sim.setTitle();
	     siv_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(siv_sim.isChecked())
				{
					siv_sim.setChecked(false);
					msp.edit().remove("sim").commit();  //解绑sim卡的同时，要将其内容从sharepreference中移除
				}
				else{
					siv_sim.setChecked(true);
					TelephonyManager telmanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = telmanager.getSimSerialNumber();
					Utils.debug("", simSerialNumber );
					msp.edit().putString("sim", simSerialNumber).commit();
					
				}
			}
		});
 }
	
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showNextPage() {
		
		if(!siv_sim.isChecked())
		{
			Utils.showToast(this, "sim未绑定");
			return;
		}
		
		startActivity(new Intent(LostSetup2.this, LostSetup3.class));
		finish();
		overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
		
	}






	/* 复写父类方法
	 * 
	 */
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(LostSetup2.this, LostSetup1.class));
		finish();
		overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
		
	}
}