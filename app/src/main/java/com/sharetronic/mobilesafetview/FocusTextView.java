/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafetview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class FocusTextView extends TextView {

	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		
		return  true;
	}

}
