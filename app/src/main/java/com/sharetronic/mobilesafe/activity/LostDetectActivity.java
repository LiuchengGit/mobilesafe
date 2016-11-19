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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Louris 201
 *
 * 
 */
public class LostDetectActivity extends Activity {
	private TextView safetel;
    private boolean isProtected;
	/* 复写父类方法
	 * 手机防盗类
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
   SharedPreferences msp = getSharedPreferences("config", MODE_PRIVATE);
   boolean configed=  msp.getBoolean("configed", false);
   if(configed)
	{  
	  //如果已经设置过（不是第一次进入手机防盗功能，则直接显示手机防盗的标题）
	   setContentView(R.layout.activity_lost);
	   safetel = (TextView) findViewById(R.id.safetel);
	   ImageView imagelock = (ImageView) findViewById(R.id.img_islock);
	   String phonesafe = msp.getString("phonesafe", "");
	   safetel.setText(phonesafe);
	   
	   isProtected = msp.getBoolean("startprotect", false);
	   if(isProtected){
		   imagelock.setImageResource(R.drawable.lock);
	   }else
	   {
		   imagelock.setImageResource(R.drawable.unlock);
	   }
	   
	   
	}
   else{
	 //  如果是第一次进入手机防盗功能，则会进入设置向导  显示标题：1.欢迎使用手机防盗 可点下一步  
	   startActivity(new Intent(LostDetectActivity.this,LostSetup1.class));
	   finish();
   }
}
	public void reEnter(View view ){
		startActivity(new Intent(LostDetectActivity.this,LostSetup1.class));
	 finish();
		
	}	
	
	
}
