package com.example.enoticeapp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpResponse;
//import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;











import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class DashBoard extends ListActivity{
	
	// Progress Dialog
	private ProgressDialog pDialog;
	ListView mListView;
	// Creating JSON Parser object
	//JSONParser jParser = new JSONParser();
	public static ArrayList<HashMap<String, String>> noticesList = new ArrayList<HashMap<String, String>>();
	public static HashMap<String,String> map;
	// url to get all products list
	private static String url_all_notices = "http://192.168.43.165/get_notices.php?flag=";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NOTICE = "notice";
	private static final String TAG_NOTICES = "notices";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DATE = "date";
	private static final String TAG_ID = "id";
	private static final String LIST_POSITION = "position";
	private static final String LIST_NUM= "num";
	//private static final String LIST_INSTANCE_STATE = "list_state";
	private static final String TAG_DESC = "description";
	private String title=null;
	private String description=null;
	private Integer id = null;
	private String datetime=null;
	//SimpleDateFormat timeFormat
	SQLiteDatabase myDb;
	Date myDate;
	String date;
	Cursor myCursor;
	private static int flag=0;
	private static int num;
	SimpleDateFormat formatter,timeFormat = new SimpleDateFormat("HH:mm:ss"), dateFormat = new SimpleDateFormat("dd/mm/yy");
	OfflineData sqlHelper = new OfflineData(this, OfflineData.database, null, OfflineData.database_version);
	
	TextView title1,description1;
	// products JSONArray
	JSONArray products = null;
	
	// Hashmap for ListView
	public static HashMap<String,String> getNoticeFromHashMap(int key) {
	        return noticesList.get(key);
	}
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dash_board);//
		///Toast.makeText(this, "ping 1", Toast.LENGTH_LONG).show();
		Log.d("ping1", "1");
		
		myDb = sqlHelper.getReadableDatabase();
    	/*myCursor = myDb.rawQuery("SELECT * from flag", null);
    	Log.d("hello","hello");
    	int cnt=myCursor.getCount();
    	Log.d("row count :",""+cnt);
    	if(myCursor.moveToFirst())
		do {
			Log.d("Dashboard SQLite", ""+myCursor.getInt(0));	
			flag = myCursor.getInt(myCursor.getColumnIndex("flag"));
		}while(myCursor.moveToNext());
    	
    	Log.d("Fetched flag Testing...",""+flag);*/
    	//flag=myCursor.getCount();
    	//int flag_num= myCursor.getCount();
    	//if(flag_num>0){
    	
    	//}
    	//else
    	//	flag=0;
    	
    	//Log.d("Flag",""+flag);
    	if(noticesList!=null)//
    		noticesList.clear();
    	if(map!=null)
    		map.clear();
    	
    	//int static lastid=0;
    	
    	//Showing local db entries
    	myCursor = myDb.rawQuery("SELECT * FROM notices", null);
    	if (myCursor.getCount()!=0){
    		myCursor.moveToFirst();
    		do {
    			Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2));
    			flag=myCursor.getInt(0);
    		}while(myCursor.moveToNext());
    	  		
    	}else
    		Log.d("Empty","empty");
    	
    	
    	Toast.makeText(getApplicationContext(), ""+flag, Toast.LENGTH_LONG).show();
    	new LoadAllNotices().execute(flag,null,null);
    	
    		ListView lv = getListView();
        
 
        // on selecting single notice
        // launching notice details
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	map = noticesList.get(position);
            	title1 = (TextView) view.findViewById(R.id.title);
            	description1 = (TextView) view.findViewById(R.id.description);
                if (title1 != null&& description1!=null)
                {
                    title1.setTypeface(null,Typeface.NORMAL);
                    description1.setTypeface(null, Typeface.NORMAL);

                }
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ShowNotice.class);
                
                in.putExtra(TAG_TITLE,map.get(TAG_TITLE));
                in.putExtra(TAG_DESC, map.get(TAG_DESC));
                in.putExtra(LIST_POSITION,position);
                in.putExtra(LIST_NUM, num);
                // starting new activity and expecting some response back
                startActivity(in);
            }
        });
	}
	
	 /**
     * Background Async Task to Load all notices by making HTTP Request
     * */
    class LoadAllNotices extends AsyncTask<Integer, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DashBoard.this);
            pDialog.setMessage("Loading notices. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            
        }
 
        /**
         * Getting All Notices from URLarg0
         * 
         */
        protected String doInBackground(Integer... args) {
            
            String str=null;
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            int fl =args[0];
            String uri= url_all_notices+fl;
            Log.d("details",url_all_notices);
            //Toast.makeText(getApplicationContext() , url_all_notices, Toast.LENGTH_LONG).show();
            //Toast.makeText(, url_all_notices, Toast.LENGTH_LONG).show();
            HttpPost myConnection = new HttpPost(uri);
            
            try {
            	response = myClient.execute(myConnection);
            	str = EntityUtils.toString(response.getEntity(), "UTF-8");
            	Log.d("response", str);
            } catch(ClientProtocolException e) {
            	e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            JSONObject json,myObj;//
        	JSONArray jsonArray,myArray;
        	String TAG="JSON Data";
            
            
            try {
        		json = new JSONObject(str);
        		jsonArray = json.getJSONArray(TAG_NOTICES);
        		for(int j=0;j<jsonArray.length();j++){
        			myObj = jsonArray.getJSONObject(j);
        			int success = myObj.getInt(TAG_SUCCESS);
        		 
        			if (success == 1) {
        				myArray = myObj.getJSONArray(TAG_NOTICE);
        				for (int i = 0 ; i < myArray.length() ; i++)
        				{
        					JSONObject myObj2 = myArray.getJSONObject(i);
        					title = myObj2.getString("title");
        					Log.d(TAG, "Title: "+title);
        					id = Integer.parseInt(myObj2.getString("id"));
        					Log.d(TAG, "Id: "+id);
        					
        					description = myObj2.getString("description");
        					Log.d(TAG, "Description: "+description);
        					datetime = myObj2.getString("date");
        					formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        					try{
        						myDate = formatter.parse(datetime);
        						if(myDate==new Date())
        							date = timeFormat.format(myDate.getTime());
        						else
        							date = dateFormat.format(myDate);
        						
        						Log.d(date,date);
        					
        					}
        					catch(ParseException e){
        						e.printStackTrace();
        					}
        					//flag=id;
        					//updating local db
        					insertNoticeInLocalDB();
        					
        					
        					
        				}

        				
        			}else{
        				Log.d(TAG,"Not Successfull");
        			}
        		}
				/*myDb = sqlHelper.getWritableDatabase();
				Log.d("new flag",""+flag);
		    	myCursor = myDb.rawQuery("UPDATE flag set flag="+flag, null);
		    	Log.d("updated flag",""+flag);*/
		    	
		    	//Check immediately
		    	/*myDb = sqlHelper.getReadableDatabase();
		    	myCursor = myDb.rawQuery("SELECT * from flag", null);
		    	Log.d("hello","hello");
		    	int cnt=myCursor.getCount();
		    	Log.d("row count :",""+cnt);
		    	if(myCursor.moveToFirst())
				do {
					Log.d("Dashboard SQLite", ""+myCursor.getString(myCursor.getColumnIndex("flag")));	
					flag = myCursor.getInt(0);
				}while(myCursor.moveToNext());
		    	
		    	Log.d("Fetched flag Testing...",""+flag);*/
		    	
        	}
        	catch(JSONException e)
        	{
        		e.printStackTrace();
        		
        	}
        	
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            
            //Fetching data from local db
            myCursor = myDb.rawQuery("SELECT * FROM "+OfflineData.table1,null);
            myCursor.moveToFirst();
            //HashMap<String, String> map; 
    		do {
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put(TAG_ID, ""+myCursor.getInt(0));
                map.put(TAG_TITLE, myCursor.getString(1));
                map.put(TAG_DESC, myCursor.getString(2));
                map.put(TAG_DATE,myCursor.getString(3));
                noticesList.add(map);//
    			Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2)+", "+myCursor.getString(3));
    		}while(myCursor.moveToNext());
            
        	
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     */
                   ListAdapter adapter = new SimpleAdapter(
                            DashBoard.this, noticesList,//
                            R.layout.list_item, new String[] { TAG_TITLE,
                                    TAG_DESC, TAG_DATE},
                            new int[] { R.id.title, R.id.description, R.id.date });
                    // updating listview
                    setListAdapter(adapter);
                    num=adapter.getCount();
                    
                }
            });
 
        }
        private void insertNoticeInLocalDB(){
        	myCursor = myDb.rawQuery("INSERT INTO "+OfflineData.table1+"("+OfflineData.title+", "+OfflineData.desc+", "+OfflineData.date+") VALUES('"+ title+"', '"+description+"', '"+date+"')",null);
    		ContentValues content = new ContentValues();
        	content.put(OfflineData.title, title);
        	content.put(OfflineData.desc, description);
        	content.put(OfflineData.date, date);
        	myDb.insert(OfflineData.table1, null, content);
        }
 
    }

}
