/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.receiver;

import com.sharetronic.mobilesafe.utils.Utils;
import com.sharetronic.mobilesafe.R;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;

/**
 * @author Louris 2015 10/8
 * 
 * 
 */
public class SmsReceiver extends BroadcastReceiver {

	/*
	 * 复写父类方法
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String phone;
		String message;
		DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName mDeviceAdminSample = new ComponentName(context,AdminReceiver.class );
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");// 使用pdus key来提取SMS
															// pdus数组
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); // 用pdu的字节数组来创建短信
				phone = msgs[i].getOriginatingAddress();
				message = msgs[i].getMessageBody();
				Utils.debug("", phone + ":" + message);

				if ("#*alarm*#".equals(message)) {
					MediaPlayer mediaPlayer = MediaPlayer.create(context,
							R.raw.ylzs);
					mediaPlayer.setVolume(1f, 1f);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
					abortBroadcast();// 不让这个广播继续传递给系统的短信接收端
				} else if ("#*location*#".equals(message)) {
					abortBroadcast();// 不让这个广播继续传递给系统的短信接收端
				} else if ("#*wipedata*#".equals(message)) {
					mDPM.wipeData(0);  
					abortBroadcast();// 不让这个广播继续传递给系统的短信接收端
				} else if ("#*lockscreen*#".equals(message)) {
					
//                    //判断一下是否激活 如果没有激活就去锁屏会崩溃   以下是跳转到激活设备管理
//					if(!mDPM.isAdminActive(mDeviceAdminSample))
//					{  //问题是	小偷不会主动去点击激活！！！！！
//					 Intent intent1 = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//					
//                     intent1.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
//                     intent1.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//                            "这是刘成的激活额");
//                     intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//                     Utils.debug("", "准备激活");
//                     context.startActivity(intent1) ;
//                     
//					}
//                      这里由本机用户第一次手动激活！					
					
					mDPM.lockNow();
					mDPM.resetPassword("123456", 0) ; //第二个参数为0即不让别的应用再次设置锁屏密码
					Utils.debug("", "已经执行锁屏");
					abortBroadcast();// 不让这个广播继续传递给系统的短信接收端
				}
			}
		}
	}

}
