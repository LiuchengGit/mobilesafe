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
import android.os.Bundle;
import android.view.View;

/**
 * @author Louris 201
 *
 * 
 */
public class AdvanceToolsActivity extends Activity {
		 /* 复写父类方法
		 * 
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_advancetools);
		}
		public void phoneSearch(View view)
		{
			Intent intent =new Intent(AdvanceToolsActivity.this, QueryAcivity.class);
			startActivity(intent);
			Utils.debug("", "归属地查询");
		}
}
