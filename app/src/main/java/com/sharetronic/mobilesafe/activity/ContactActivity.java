/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharetronic.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @author Louris 2015/10/7
 *
 * 
 */
public class ContactActivity extends Activity {
 /* 复写父类方法
 * 
 */
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_contact);
	ListView contactlistv = (ListView) findViewById(R.id.contactlv);
	List<HashMap<String,String>> readContact = readContact();
	contactlistv.setAdapter(new SimpleAdapter(this, readContact,
			R.layout.list_item, new String[] { "name", "phonenum" },
			new int[] { R.id.tv_name, R.id.tv_phone }));
	contactlistv.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Adapter adapter=arg0.getAdapter();
            Map<String,String> map=(Map<String, String>) adapter.getItem(arg2);
            String phone = map.get("phonenum");
            Intent intent = new Intent();
            intent.putExtra("phone", phone);
            setResult(Activity.RESULT_OK,intent);
            finish();
		}
	});
}
/**
 * @return
 * 
 */
private List<HashMap<String,String>> readContact() {

	Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
	Uri uri_data = Uri.parse("content://com.android.contacts/data");
	Cursor result = getContentResolver().query(uri,
			new String[] { "contact_id" }, null, null, null);
	ArrayList<HashMap<String,String>> maplist = new ArrayList<HashMap<String,String>>();
	if (result != null) {
		while (result.moveToNext()) {
			String id = result.getString(0);
			// Log.v("sharetronic",id );

			Cursor result1 = getContentResolver().query(uri_data,
					new String[] { "data1", "mimetype" }, "contact_id = ?",
					new String[] { id }, null);
			if (result1 != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				while (result1.moveToNext()) {

					String data = result1.getString(0);
					String minetype = result1.getString(1);
					if ("vnd.android.cursor.item/phone_v2".equals(minetype)) {
						map.put("phonenum", data); 
					} else if ("vnd.android.cursor.item/name"
							.equals(minetype)) {
						map.put("name", data);
					}
				}
				maplist.add(map);
				result1.close();
			}
		}
		result.close();
	}
	return maplist;
}

}
