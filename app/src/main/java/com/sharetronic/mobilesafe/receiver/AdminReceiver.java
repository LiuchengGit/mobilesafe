/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.receiver;

import com.sharetronic.mobilesafe.utils.Utils;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;

/**
 * @author Louris 2015 10/9
 * 需要把应用程序升级为拥有系统管理员权限，写一个广播接收者，给该广播接收者去申请系统管理员的权限
 * 让操作系统给广播接收者授权。
 * （其实就去激活系统的一个授权的组件）让用户自己激活。
 * 
 */
public class AdminReceiver extends DeviceAdminReceiver {


}
