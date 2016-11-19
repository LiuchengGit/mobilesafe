/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Louris 2015/9/28
 * 
 * 
 */
public class HomeActivity extends Activity {
	private String[] mems = new String[] { "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

	private int[] mPics = new int[] { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };

	private SharedPreferences msp;

	public class MyGvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 9;
		}

		@Override
		public Object getItem(int position) {
			return mems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = View.inflate(HomeActivity.this,
					R.layout.home_list_item, null);
			TextView gitem_textview = (TextView) view
					.findViewById(R.id.gitem_textview);
			ImageView imview = (ImageView) view.findViewById(R.id.imview);
			gitem_textview.setText(mems[position]);
			imview.setImageResource(mPics[position]);

			return view;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeactivity);
		msp = getSharedPreferences("config", MODE_PRIVATE);
		GridView gridView = (GridView) findViewById(R.id.gridview);
		MyGvAdapter adapter = new MyGvAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0: {
					showSecretDialog();
					break;
				}
                
				case 7: {
					startActivity(new Intent(HomeActivity.this,
							AdvanceToolsActivity.class));
					break;
				}
				case 8: {
					startActivity(new Intent(HomeActivity.this,
							SettingsActivity.class));
					break;
				}

				default:
					break;
				}

			}
		});

	}

	/**
	 * param return
	 */
	protected void showSecretDialog() {
		msp = getSharedPreferences("config", MODE_PRIVATE);
		String password = msp.getString("password", null);
		if (!TextUtils.isEmpty(password)) {
			showInputDialog();
		} else {
			showSetSecretDialog();
		}

	}

	/**
	 * param return
	 */
	private void showInputDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog alertDialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_secret_input, null);
		Button btn_setok = (Button) view.findViewById(R.id.btn_setok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);

		final EditText tv_entersecret = (EditText) view
				.findViewById(R.id.tv_entersecret);
		final EditText tv_entersecretagain = (EditText) view
				.findViewById(R.id.tv_entersecretagain);
		alertDialog.setView(view, 0, 0, 0, 0);
		alertDialog.show();
		btn_setok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = tv_entersecret.getText().toString();
				String savedPassword = msp.getString("password", null);
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(HomeActivity.this, "输入密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else {

					if (MD5Utils.encodeMD5(password).equals(savedPassword)) {
//						Toast.makeText(HomeActivity.this, "登陆成功",
//								Toast.LENGTH_SHORT).show();
						alertDialog.dismiss();
						startActivity(new Intent(HomeActivity.this,LostDetectActivity.class));
					} else {
						Toast.makeText(HomeActivity.this, "密码错误",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
	}

	/**
	 * 没有设置过密码，第一次设置 return
	 */
	private void showSetSecretDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog alertDialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_secret_set, null);
		Button btn_setok = (Button) view.findViewById(R.id.btn_setok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);

		final EditText tv_entersecret = (EditText) view
				.findViewById(R.id.tv_entersecret);
		final EditText tv_entersecretagain = (EditText) view
				.findViewById(R.id.tv_entersecretagain);
		alertDialog.setView(view, 0, 0, 0, 0);
		alertDialog.show();
		btn_setok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tvsecret = tv_entersecret.getText().toString();
				String tvsecretConfirm = tv_entersecretagain.getText()
						.toString();
				if (!TextUtils.isEmpty(tvsecret)
						&& !TextUtils.isEmpty(tvsecretConfirm)) {
					if (tvsecret.equals(tvsecretConfirm)) {
//						Toast.makeText(HomeActivity.this, "登陆成功",
//								Toast.LENGTH_SHORT).show();
						msp.edit().putString("password", MD5Utils.encodeMD5(tvsecret)).commit();
						alertDialog.dismiss();
						startActivity(new Intent(HomeActivity.this,LostDetectActivity.class));
					} else {

						Toast.makeText(HomeActivity.this, "两次密码不一致",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(HomeActivity.this, "输入密码不能为空",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
	}

}
