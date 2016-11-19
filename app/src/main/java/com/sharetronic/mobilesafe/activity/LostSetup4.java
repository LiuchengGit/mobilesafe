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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Louris 2015
 *
 * 如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：2.
 */
public class LostSetup4 extends BaseActivity {
	private CheckBox  cBox;
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.lost__setup4);
	    cBox = (CheckBox) findViewById(R.id.cb_protect);
	    if(msp.getBoolean("startprotect", false))
	    {
			cBox.setText("防盗保护已经开启");
			cBox.setChecked(true);
	    }
	    
	    cBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if( isChecked){
					msp.edit().putBoolean("startprotect",true ).commit();
					cBox.setText("防盗保护已经开启");
				}else
				{
					cBox.setText("防盗保护没有开启");
					//msp.edit().remove("startprotect").commit();
					msp.edit().putBoolean("startprotect",false ).commit();
				}
				
			}
		});
 }
	/* 复写父类方法
	 * 
	 */
	@Override
	public void next(View view) {
		msp.edit().putBoolean("configed", true).commit();
		super.next(view);
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(LostSetup4.this, LostSetup3.class));
		finish();
		overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
		
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showNextPage() {
		startActivity(new Intent(LostSetup4.this,LostDetectActivity.class ));
		finish();
		overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
		
	}
}