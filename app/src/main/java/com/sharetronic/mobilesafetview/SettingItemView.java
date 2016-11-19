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
public class SettingItemView extends RelativeLayout {

	public TextView settingtitletv;
	public TextView settingdescriptiontv;
	public CheckBox checkboxsetting;
	private final String NAMESPACE ="http://schemas.android.com/apk/res/com.sharetronic.mobilesafe";
	private String mTitle;
	private String mdesc_on;
	private String mdesc_off;
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		
		super(context, attrs, defStyle);
		Utils.debug("", "我用的是这个构造方法----1");
		 initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Utils.debug("", "我用的是这个构造方法----2");
//		 int attributeCount = attrs.getAttributeCount();
//		 for(int i=0;i<attributeCount;i++){
//			Utils.debug("", attrs.getAttributeValue(i)); 
//		 }
		mTitle=attrs.getAttributeValue(NAMESPACE , "title");
		mdesc_on=attrs.getAttributeValue(NAMESPACE , "description_on");
		mdesc_off=attrs.getAttributeValue(NAMESPACE , "description_off");
		
		 
		 initView();
	}

	public SettingItemView(Context context) {
		super(context);
		Utils.debug("", "我用的是这个构造方法----3");
		 initView();
	}
  private void initView(){
	 View.inflate(getContext(), R.layout.setting_item_view, this);
	 settingtitletv = (TextView) findViewById(R.id.tv_settingtitle);
	 settingdescriptiontv = (TextView) findViewById(R.id.tv_settingdescription);
	 checkboxsetting = (CheckBox) findViewById(R.id.checkbox_setting);
	 
	 
	 
  }
  public void setTitle() {
	this.settingtitletv.setText(mTitle);
}

public void setDescription(String str) {
	this.settingdescriptiontv.setText(str);
}

public boolean isChecked(){
	return  checkboxsetting.isChecked();
  }
  public void setChecked(Boolean checked){
	  if(checked)
	  {
		  checkboxsetting.setChecked(checked);
		  setDescription(mdesc_on);
	  }else{
		  checkboxsetting.setChecked(checked); 
		  setDescription(mdesc_off);
	  }
  }
}
