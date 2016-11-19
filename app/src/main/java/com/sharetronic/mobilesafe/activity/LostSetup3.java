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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * @author Louris 2015
 *
 * 如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：2.
 */
public class LostSetup3 extends BaseActivity  {
	
	private EditText et_phone;
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     setContentView(R.layout.lost__setup3);
	     et_phone = (EditText) findViewById(R.id.et_phone);
	     String phone = msp.getString("phonesafe", "");
	     et_phone.setText(phone);
	     
 }
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(LostSetup3.this, LostSetup2.class));
		finish();
		overridePendingTransition(R.anim.trans_previous_in,R.anim.trans_previous_out);
		
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	public void showNextPage() {
		String phonenum = et_phone.getText().toString().trim();
		
		if(TextUtils.isEmpty(phonenum))
		{
		  	Utils.showToast(this, "安全号码不能为空");
		  	return ;
		}
		msp.edit().putString("phonesafe",phonenum ).commit();
		
		startActivity(new Intent(LostSetup3.this, LostSetup4.class));
		finish();
		overridePendingTransition(R.anim.trans_in,R.anim.trans_out);
		
	}
	public void selectContact(View view){
		
		Intent intent = new Intent(LostSetup3.this,ContactActivity.class);
		startActivityForResult(intent, 55);
	}
	/* 复写父类方法
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Utils.debug("",requestCode+"" );
		Utils.debug("",resultCode+"" );
		if(resultCode ==Activity.RESULT_OK)
		{
			String phone = data.getStringExtra("phone");
			phone =phone.replaceAll("-", "").replaceAll(" ", "");
		    et_phone.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}