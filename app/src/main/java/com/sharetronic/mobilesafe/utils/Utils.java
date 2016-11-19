/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class Utils {

	/**
	 * @param string
	 * @param string2
	 */
	public static void debug(String string, String string2) {
		if(Constant.debug==1)
		{
			Log.e("sharetronic", string2);
			
		}
		else return;
	}

	/**
	 * @param inputStream
	 * @return 
	 * @throws IOException 
	 */
	public static String readStringFromStream(InputStream inputStream) throws IOException {
             ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
             int len=0;
             byte[] b = new byte[1024];
             while((len=inputStream.read(b))!=-1){
            	 arrayOutputStream.write(b, 0, len);
             }
             inputStream.close();
             arrayOutputStream.close();
             return arrayOutputStream.toString();
	    }
  public static void showToast(Context context,CharSequence text){
	  Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
  }
  public static void showLongToast(Context context,CharSequence text){
	  Toast.makeText(context, text, Toast.LENGTH_LONG).show();
  }
}
