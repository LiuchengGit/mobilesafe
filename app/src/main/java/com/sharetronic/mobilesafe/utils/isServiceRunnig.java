/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * @author Louris 201
 *
 * 
 */
public class isServiceRunnig {

	public static boolean isServiceRunning(Context ctx ,String ServiceName){
		
		
		ActivityManager activityManager = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
		for(RunningServiceInfo serviceinfo : runningServices)
		{
			
			String serviceclassName = serviceinfo.service.getClassName();
			Utils.debug("",serviceclassName);
			if(serviceclassName.equals(ServiceName))
		      return true;
		}
		return false;
		
	}
	
}
