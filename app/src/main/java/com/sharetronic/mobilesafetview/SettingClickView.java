/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafetview;

import com.sharetronic.mobilesafe.R;
import com.sharetronic.mobilesafe.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Louris 2015/9/28
 *
 * 
 */
public class SettingClickView extends RelativeLayout {

	public TextView settingtitletv;
	public TextView settingdescriptiontv;
	
	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		
		super(context, attrs, defStyle);
		 initView();
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 
		 initView();
	}

	public SettingClickView(Context context) {
		super(context);
		 initView();
	}
  private void initView(){
	 View.inflate(getContext(), R.layout.setting_click_view, this);
	 settingtitletv = (TextView) findViewById(R.id.tv_settingtitle);
	 settingdescriptiontv = (TextView) findViewById(R.id.tv_settingdescription);
	 
	 
	 
  }
  public void setTitle(String mTitle) {
	this.settingtitletv.setText(mTitle);
}

public void setDescription(String str) {
	this.settingdescriptiontv.setText(str);
}


}
