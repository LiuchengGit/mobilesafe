/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.service.AddressService;
import com.sharetronic.mobilesafe.utils.Utils;
import com.sharetronic.mobilesafe.utils.isServiceRunnig;
import com.sharetronic.mobilesafetview.SettingClickView;
import com.sharetronic.mobilesafetview.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * @author Louris 2015/9/28
 * 
 * 
 */
public class SettingsActivity extends Activity {
	private SettingItemView siv_update;
	private SettingItemView siv_address;
	private SharedPreferences msp;
	private SettingClickView siv_addressstyle;
	private SettingClickView siv_addressstylelocation;
	
     String [] items = {"活力橙","金属灰","苹果绿","半透明","卫士蓝"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_center);
		msp = getSharedPreferences("config", MODE_PRIVATE);
		initAutoUpdate();
		initAddress();
		initAddressStyle();
		initAddressStyleLocation();
	}
	
   




public void initAutoUpdate(){
		
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		siv_update.setTitle();
		boolean autoUpdate = msp.getBoolean("isAutoUpdate", true);
		if (autoUpdate) {
			siv_update.setChecked(true);
		} else {
			siv_update.setChecked(false);
		}

		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_update.isChecked()) {
					siv_update.setChecked(false);
					msp.edit().putBoolean("isAutoUpdate", false).commit();
				} else {
					siv_update.setChecked(true);
					msp.edit().putBoolean("isAutoUpdate", true).commit();
				}
			}
		});
	}

	public void initAddress(){
		siv_address = (SettingItemView) findViewById(R.id.siv_address);
		siv_address.setTitle();
		//归属地显示是一个service，必须根据实时的服务运行状态来更新归属地显示
		if(isServiceRunnig.isServiceRunning(this, "com.sharetronic.mobilesafe.service.AddressService"))
		{   
			siv_address.setChecked(true);
		}else
		{
			Utils.debug("", "电话归属地服务关闭状态");
			siv_address.setChecked(false);
		}
		
		
		siv_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_address.isChecked()) {
					siv_address.setChecked(false);
					Intent service =new Intent(SettingsActivity.this, AddressService.class);
					stopService(service);
					//msp.edit().putBoolean("isAutoUpdate", false).commit();
				} else {
					siv_address.setChecked(true);
					Intent service =new Intent(SettingsActivity.this, AddressService.class);
					startService(service);
					//msp.edit().putBoolean("isAutoUpdate", true).commit();
				}
			}
		});
	}
	/**
	 * param
	 * return
	 */
	private void initAddressStyle() {
		siv_addressstyle = (SettingClickView) findViewById(R.id.siv_addressstyle);
		siv_addressstyle.setTitle("设置电话归属地提示框的风格");
		
		int style =  msp.getInt("addressstyle", 0);
		siv_addressstyle.setDescription(items[style]);
		
		siv_addressstyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  showStyleSelectDialog();	
			}

			
		});
		
		
	}
	private void showStyleSelectDialog() {

           AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
           builder.setTitle("归属地提示框风格");
          builder.setIcon(R.drawable.main_icon);
           
          // String [] items = {"活力橙","金属灰","苹果绿","半透明","卫士蓝"};
           
           
           int style =  msp.getInt("addressstyle", 0);
		   builder.setSingleChoiceItems(items,style,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 msp.edit().putInt("addressstyle", which).commit();
				 siv_addressstyle.setDescription(items[which]);
				 dialog.dismiss();
			}
		 });
           builder.setNegativeButton("取消", null);
           builder.show();
		
	}
	/**
	 * param
	 * return
	 */
	private void initAddressStyleLocation() {
		siv_addressstylelocation = (SettingClickView) findViewById(R.id.siv_addressstylelocation);
		siv_addressstylelocation.setTitle("电话归属地提示框的位置");
		


		siv_addressstylelocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  Intent intent = new Intent(SettingsActivity.this, DragActivity.class);
                  startActivity(intent);
			}

			
		});
	}

}
