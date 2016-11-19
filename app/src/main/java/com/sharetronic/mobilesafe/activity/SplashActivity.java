package com.sharetronic.mobilesafe.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.MD5Utils;
import com.sharetronic.mobilesafe.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	
	// 服务器返回的信息
	private String mversionName;
	private int mVersionCode;
	private String mdescription;
	private String msoftUrl;
	
	protected final int Access_Update_Sucess = 0;
	protected final int NET_ERROR = 1;
	protected final int JSON_ERROR = 2;
	protected final int Access_URL_ERROR = 3;
	protected final int Enter_Home_Activity = 4;
    private TextView tv_progressbar;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Access_Update_Sucess:
				showUpadateDialog();
				break;
			case NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT)
						.show();
				 enterHomeActivity();
				break;
			case JSON_ERROR:
				Toast.makeText(SplashActivity.this, "数据解析错误",
						Toast.LENGTH_SHORT).show();
				 enterHomeActivity();
				break;
			case Access_URL_ERROR:
				Toast.makeText(SplashActivity.this, "资源连接错误",
						Toast.LENGTH_SHORT).show();
				 enterHomeActivity();
				break;
			case Enter_Home_Activity:
				 enterHomeActivity();
				break;
			default:
				break;
			}
		}
	};
	private RelativeLayout rtlayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView tv_version = (TextView) findViewById(R.id.tv_version);
		 tv_progressbar = (TextView)findViewById(R.id.tv_progressbar);
	    
		tv_version.setText("版本号" + getVersionName());
		SharedPreferences msp = getSharedPreferences("config", MODE_PRIVATE);
		copyDB("address.db");
		 boolean isAutoUpdate = msp.getBoolean("isAutoUpdate", true);
        if(isAutoUpdate){
        	  Utils.debug("", "之前已经设置自动更新");
              checkVersion();
        }
        else{
        Utils.debug("", "用handler发送延时两秒的消息");
        	  handler.sendEmptyMessageDelayed(Enter_Home_Activity, 2000);
        }
		rtlayout = (RelativeLayout)findViewById(R.id.rt_layout);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1f);
		alphaAnimation.setDuration(2000);
		rtlayout.setAnimation(alphaAnimation);

	}
	/**
	 * param
	 * return
	 */
	public   void copyDB(String string) {
		    File file = new File(getFilesDir(),string);
		    Utils.debug("", getFilesDir()+"");
		    InputStream in = null;
		    FileOutputStream out=null;
		    if(file.exists()){
		    	Utils.debug("", "数据库已经存在，不需要拷贝");
		    	return;
		    }
		 try {
			in = getResources().getAssets().open(string);
		    out = new FileOutputStream (file);
		    
			byte[] buffer = new byte[1024];
			int len =0;
			while((len=in.read(buffer))!= -1){
				out.write(buffer, 0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Utils.debug("", ""+requestCode);
		Utils.debug("", ""+resultCode);
		if(requestCode==123){
			enterHomeActivity();
		}
		
	}
	/**
	 * 
	 */
	protected void enterHomeActivity() {
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 
	 */
	protected void showUpadateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("最新版本：" + mVersionCode);
		builder.setMessage("描述信息：" + mdescription);

		//builder.setCancelable(false);
		builder.setPositiveButton("立即更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Utils.debug("", "开始更新");
				downLoad();
			}
		});
		builder.setNegativeButton("稍后再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Utils.debug("", "稍后再说");
				 enterHomeActivity();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				 enterHomeActivity();
			}
		});
		builder.show();
	}

	/**
	 * 
	 */
	protected void downLoad() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{	
	    tv_progressbar.setVisibility(TextView.VISIBLE);
	    
			 
		HttpUtils httpUtils = new HttpUtils();
		String target = Environment.getExternalStorageDirectory() + "/update.apk";
		httpUtils.download(msoftUrl, target, new RequestCallBack<File>() {
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				
				Utils.debug("",current+"/"+total);
				 tv_progressbar.setText("下载进度："+ current *100 /total + "%");
			}
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				Utils.debug("","下载成功");
				installApp(arg0.result);
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Utils.debug("","下载失败");
			}
		});
	  }else{
		  
		  Toast.makeText(SplashActivity.this, "未检测到sdcard",Toast.LENGTH_SHORT).show();
	  }
	}

	/**
	 * 
	 */
	private void checkVersion() {
		
		new Thread() {
			int starttime = (int) System.currentTimeMillis();
			Message msg = Message.obtain();
			HttpURLConnection con = null;
			
			
			public void run() {
				try {
					URL url = new URL("http://192.168.1.106:8888/update.json");
					con = (HttpURLConnection) url
							.openConnection();
					// 这里只是创建HttpURLConnection对象，即使网络不通，或服务器未开，不会引起异常
					con.setReadTimeout(2000);
					con.setConnectTimeout(2000);
					con.setRequestMethod("GET");
					con.connect(); // 服务器关闭的情况下，执行connect会立即进入io Exception
					int responseCode = con.getResponseCode();
					Utils.debug("", "" + responseCode);
					if (responseCode == 200) {
						Utils.debug("", "URL访问成功");
						InputStream inputStream = con.getInputStream();
						String replystring = Utils
								.readStringFromStream(inputStream);
						Utils.debug("", replystring);
						JSONObject jsonObject = new JSONObject(replystring);
						mversionName = jsonObject.getString("versionName");
						mVersionCode = jsonObject.getInt("versionCode");
						mdescription = jsonObject.getString("description");
						msoftUrl = jsonObject.getString("Url");
						Utils.debug("", "mVersionCode"+mVersionCode);
						Utils.debug("", "getVersionCode"+getVersionCode());
						if (mVersionCode > getVersionCode()
								&& (getVersionCode() != -1)) {
							msg.what = Access_Update_Sucess;
						}
						else {
							msg.what = Enter_Home_Activity;
							
						}
					} else {
						Utils.debug("", "访问失败");
						return;
					}

				} catch (MalformedURLException e) {
					msg.what = Access_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e1) {
					msg.what = NET_ERROR;
					e1.printStackTrace();
				} catch (JSONException e2) {
					Utils.debug("", "json访问失败"+e2.toString());
					msg.what = JSON_ERROR;
					e2.printStackTrace();
				} finally {
					if(con !=null)
					{con.disconnect();}
					int usedtime  = (int) (System.currentTimeMillis() - starttime);
					if(usedtime<2000)
					{
						try {
							Thread.sleep(2000-usedtime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					    handler.sendMessage(msg);
				}

			};

		}.start();

	}

	private String getVersionName() {

		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.sharetronic.mobilesafe", 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	private int getVersionCode() {

		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.sharetronic.mobilesafe", 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	private void installApp(File appFile){
    	//创建URI 
    	Uri uri = Uri.fromFile(appFile); 
    	//创建Intent意图 
    	Intent intent = new Intent(Intent.ACTION_VIEW); 
    	//设置Uri和类型 
    	intent.setDataAndType(uri, "application/vnd.android.package-archive"); 
    	//执行意图进行安装 
    	startActivityForResult(intent,123);
    }
	
}
