/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.receiver;

import com.sharetronic.mobilesafe.utils.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;

/**
 * @author Louris 2015 10/3
 * 
 * 
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	/*
	 * 复写父类方法
	 */
	@Override
	public void onReceive(Context context, Intent arg1) {
		SharedPreferences msp = context.getSharedPreferences("config",
				context.MODE_PRIVATE);

		boolean isProtected = msp.getBoolean("startprotect", false);
		if (isProtected) {
			String sim = msp.getString("sim", null);
			if (!TextUtils.isEmpty(sim)) {
				TelephonyManager telephonyManager = (TelephonyManager) context
						.getSystemService(context.TELEPHONY_SERVICE);
				String currentsimSerialNumber = telephonyManager
						.getSimSerialNumber() +"111";

				if (sim.equals(currentsimSerialNumber)) {
					Utils.debug("", "sim卡未更换手机很安全");
				} else {
					Utils.debug("", "手机sim已经更换");
					String phonenum = msp.getString("phonesafe", "");
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phonenum, null, "louris sim card is changed",null,null);
				}
			}
		}
	}

}
