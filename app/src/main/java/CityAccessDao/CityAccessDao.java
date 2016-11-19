/**
 * @functon
 * @author
 * @param
 * @return
 */
package CityAccessDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Louris 2015/10/9
 * 
 * 
 */
public class CityAccessDao {

	private static final String PATH = "/data/data/com.sharetronic.mobilesafe/files/address.db";

	public static String getCity(String phonenum) {
		String city ="未知号码";
		SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null,
				SQLiteDatabase.OPEN_READONLY);
		
		
	 if(phonenum.matches("^1[3-8]\\d{9}$"))
	 {
			
		  Cursor rawQuery = database.rawQuery(
					"select location from data2 where id=(select outkey from data1 where id=?)",
					new String[] {phonenum.substring(0, 7)});
	      if(rawQuery.moveToNext())
	      {
	    	  city = rawQuery.getString(0);
	      }
	      rawQuery.close();
	 }else if(phonenum.matches("^\\d+$") ){
		 switch(phonenum.length()){
		 case 3 : city="报警电话";break;
		 case 4 : city="模拟器";break;
		 case 5 : city="客服电话";break;
		 case 7 : 
		 case 8 : city="本地电话";break;
		 default :
			     if(phonenum.startsWith("0")&&phonenum.length()>10)
			     {
			    	 
			    	 Cursor rawQuery = database.rawQuery(
								"select location from data2 where area=?",
								new String[] {phonenum.substring(1, 4)});
				      if(rawQuery.moveToNext())
				      {
				    	  city = rawQuery.getString(0);
				      }else {
				    	  
				    		 Cursor rawQuery1 = database.rawQuery(
										"select location from data2 where area=?",
										new String[] {phonenum.substring(1, 3)}); 
				    		 if(rawQuery.moveToNext())
						      {
						    	  city = rawQuery.getString(0);
						      }
				      }
				      rawQuery.close();	 
			     }
			 ;break;
		 }
	 }
	 
	 database.close();
	 return city;		
			
			
	}

}
