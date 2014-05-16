package com.example.enoticeapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
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
	private static final String TAG_ID = "id";
	private static final String LIST_POSITION = "position";
	private static final String LIST_NUM= "num";
	//private static final String LIST_INSTANCE_STATE = "list_state";
	private static final String TAG_DESC = "description";
	private String title=null;
	private String description=null;
	private String id = null;
	SQLiteDatabase myDb;
	Cursor myCursor;
	private static int flag;
	private static int num;
	OfflineData sqlHelper = new OfflineData(this, OfflineData.database, null, OfflineData.database_version);
	
	// products JSONArray
	JSONArray products = null;
	
	// Hashmap for ListView
	public static HashMap<String,String> getNoticeFromHashMap(int key) {
	        return noticesList.get(key);
	}
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dash_board);
		
		myDb = sqlHelper.getWritableDatabase();
    	myCursor = myDb.rawQuery("SELECT * FROM "+OfflineData.table, null);
    	flag = myCursor.getCount();
    	Log.d("Flag",""+flag);
    	if(noticesList!=null)
    		noticesList.clear();
    	if(map!=null)
    		map.clear();
    	
    	new LoadAllNotices().execute(flag,null,null);
    	if (myCursor.getCount()!=0){
    		myCursor.moveToFirst();
    		do {
    			Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2));
    		}while(myCursor.moveToNext());
    	  		
    	}else
    		Log.d("Empty","empty");
    	
    		ListView lv = getListView();
        
 
        // on selecting single notice
        // launching notice details
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	map = noticesList.get(position);
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
         * Getting All Notices from URL
         * 
         */
        protected String doInBackground(Integer... args) {
            
            String str=null;
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            Integer flag = args[0];
            url_all_notices+=""+flag;
            HttpPost myConnection = new HttpPost(url_all_notices);
            
            try {
            	response = myClient.execute(myConnection);
            	str = EntityUtils.toString(response.getEntity(), "UTF-8");
            	Log.d("response", str);
            } catch(ClientProtocolException e) {
            	e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            JSONObject json,myObj;
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
        					id = myObj2.getString("id");
        					Log.d(TAG, "Id: "+id);
        					
        					description = myObj2.getString("description");
        					Log.d(TAG, "Description: "+description);
        					
        					//updating local db
        					
        					myCursor = myDb.rawQuery("INSERT INTO "+OfflineData.table+"("+OfflineData.title+", "+OfflineData.desc+") VALUES('"+ title+"', '"+description+"')",null);
        		    		ContentValues content = new ContentValues();
        		        	content.put(OfflineData.title, title);
        		        	content.put(OfflineData.desc, description);
        		        	myDb.insert(OfflineData.table, null, content);
        					
        					
        					
        				}
        			}else{
        				Log.d(TAG,"Not Successfull");
        			}
        		}
        		
        	}
        	catch(JSONException e)
        	{
        		e.printStackTrace();ListView lv = getListView();
        		
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
            myCursor = myDb.rawQuery("SELECT * FROM "+OfflineData.table,null);
            myCursor.moveToFirst();
            //HashMap<String, String> map; 
    		do {
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put(TAG_ID, ""+myCursor.getInt(0));
                map.put(TAG_TITLE, myCursor.getString(1));
                map.put(TAG_DESC, myCursor.getString(2));
                noticesList.add(map);
    			Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2));
    		}while(myCursor.moveToNext());
            
        	
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                   ListAdapter adapter = new SimpleAdapter(
                            DashBoard.this, noticesList,
                            R.layout.list_item, new String[] { TAG_TITLE,
                                    TAG_DESC},
                            new int[] { R.id.title, R.id.description });
                    // updating listview
                    setListAdapter(adapter);
                    num=adapter.getCount();
                    
                }
            });
 
        }
 
    }

}
