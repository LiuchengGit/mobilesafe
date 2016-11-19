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

/**
 * @author Louris 2015
 *
 * 如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：1.欢迎使用手机防盗 可点下一步  
 */
public class LostSetup1 extends BaseActivity{
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     setContentView(R.layout.lost__setup1);
 }
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showPreviousPage() {
		
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showNextPage() {
		startActivity(new Intent(LostSetup1.this, LostSetup2.class));
		finish();
		overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
		
	}
}