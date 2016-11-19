/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import com.sharetronic.mobilesafe.R;

import CityAccessDao.CityAccessDao;
import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Louris 201
 *
 * 
 */
public class QueryAcivity extends Activity {
    private EditText ev_phonenum;
	private TextView tv_result;
    private String city =null;
	/* 复写父类方法
     * 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.activity_query);
    	ev_phonenum = (EditText) findViewById(R.id.ev_phonenum);
    	tv_result = (TextView) findViewById(R.id.tv_result);
    	ev_phonenum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s.toString()))
			     {
			    		city = CityAccessDao.getCity(s.toString());
			    		tv_result.setText(city);
			     }	
			      else
			      {
			    	  city="查询结果";
			    	  tv_result.setText(city);	  
			    			  
			      }
			    	
			    
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
    	
    }
    public void query(View view){
    	
    	String phonenum = ev_phonenum.getText().toString().trim();
    	if(!TextUtils.isEmpty(phonenum))
     {
    		city = CityAccessDao.getCity(phonenum);
    		tv_result.setText(city);
     }	
      else
      {
    	  Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
    	  ev_phonenum.startAnimation(shake);
    	  vibrate();
    	  city="查询结果";
    	  tv_result.setText(city);	  
    			  
      }
    	
    }
	/**
	 * param
	 * return
	 */
	private void vibrate() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		//vibrator.vibrate(2000);
		vibrator.vibrate(new long[]{2000,3000,2000,4000}, 0);
		//vibrator.cancel();
	}	
		
}
