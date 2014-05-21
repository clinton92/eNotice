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
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
//import com.example.enoticeapp.ActionMode;
import android.view.ActionMode;

public class DashBoard extends ListActivity implements OnQueryTextListener{
	
	// Progress Dialog
	private ProgressDialog pDialog;
	ListView mListView;
	ListAdapter adapter;
	CheckBox c;
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
	View listItem;
	ListView lv;
	private static int flag=0;
	private static int num;
	SimpleDateFormat formatter,timeFormat = new SimpleDateFormat("HH:mm:ss"), dateFormat = new SimpleDateFormat("dd/mm/yy");
	OfflineData sqlHelper = new OfflineData(this, OfflineData.database, null, OfflineData.database_version);
	private ActionMode mActionMode;
	
	TextView title1,description1;
	// products JSONArray
	JSONArray products = null;
	
	// Hashmap for ListView
	public static HashMap<String,String> getNoticeFromHashMap(int key) {
	        return noticesList.get(key);
	}
	 MultiChoiceModeListener mMultiChoiceModeListener;
	 
	    @Override
	    protected void onStart() {
	        super.onStart();
	 
	        /** For contextual action mode, the choice mode should be CHOICE_MODE_MULTIPLE_MODAL */
	        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	 
	        /** Setting multichoicemode listener for the listview */
	        getListView().setMultiChoiceModeListener(mMultiChoiceModeListener);
	    }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	     // Inflate the menu; this adds items to the action bar if it is present.
	     getMenuInflater().inflate(R.menu.main, menu);
	     SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
	           SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
	    
	           searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	           searchView.setSubmitButtonEnabled(true);
	           searchView.setOnQueryTextListener(this);
	    
	           return super.onCreateOptionsMenu(menu);
	    }
	    
	    @Override
	    public boolean onQueryTextChange(String newText)
	    {
	     // this is your adapter that will be filtered
	         if (TextUtils.isEmpty(newText))
	         {
	               lv.clearTextFilter();
	           }
	         else
	         {
	               lv.setFilterText(newText.toString());
	           }
	            
	         return true;
	    }
	    
	    @Override
	    public boolean onQueryTextSubmit(String query) {
	     // TODO Auto-generated method stub
	     return false;
	    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dash_board);//
		///Toast.makeText(this, "ping 1", Toast.LENGTH_LONG).show();
		Log.d("ping1", "1");
		
		myDb = sqlHelper.getReadableDatabase();
    	
    	if(noticesList!=null)//
    		noticesList.clear();
    	if(map!=null)
    		map.clear();
    	
    	//Showing local db entries in Log and setting flag with last id.
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
    	
    	lv = getListView();
    	lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    	lv.setTextFilterEnabled(true);

    		
    		
    		/** Defining a multichoicemodelistener for the listview. */
           /* mMultiChoiceModeListener = new MultiChoiceModeListener() {
     
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
     
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
     
                /** This will be invoked when action mode is created. In our case , it is on long clicking a menu item */
             /*   @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    getMenuInflater().inflate(R.menu.contextual_menu, menu);
                    return true;
                }
     
                /** Invoked when an action in the action mode is clicked */
               /* @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    Toast.makeText(getBaseContext(), "Applying "+ item.getTitle() + " on "+ getListView().getCheckedItemCount() + " Rivers \n" + getCheckedItems(), Toast.LENGTH_LONG).show();
                    return false;
                }
               @Override
                public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2, boolean arg3) {
                  
                 //final int checkedCount = lv.getCheckedItemCount();
                 arg0.setTitle("1 Selected");
                 //adapter.toggleSelection(arg1);
                }/*
                @Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// 	TODO Auto-generated method stub

					if(isChecked)
						mActionMode = DashBoard.this.startActionMode(new ActionBarCallBack());
					else
						mActionMode.finish();
				}*/
                
            //};
            
         		
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	TextView myId =(TextView) view.findViewById(R.id.noticeId);
            	int myid=Integer.parseInt(myId.getText().toString());
            	Log.d("Id of clicked item",""+myid);
            	//map = noticesList.get(position);
            	
            	title1 = (TextView) view.findViewById(R.id.title);
            	description1 = (TextView) view.findViewById(R.id.description);
                if (title1 != null&& description1!=null)
                {
                    title1.setTypeface(null,Typeface.NORMAL);
                    description1.setTypeface(null, Typeface.NORMAL);

                }
                /*CheckBox checkbox =(CheckBox) view.findViewById(R.id.checkBox1);
        		if(checkbox!=null){
        			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            	  
        				@Override
        				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        					// 	TODO Auto-generated method stub
      
        					if(isChecked)
        						mActionMode = DashBoard.this.startActionMode(new ActionBarCallBack());
        					else
        						mActionMode.finish();
        				}
        			});
        		}*/
                
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ShowNotice.class);
                
                in.putExtra("id",myid);
                in.putExtra(LIST_POSITION,position);
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
        					
        					//updating local db
        					insertNoticeInLocalDB();
        					
        					
        					
        				}

        				
        			}else{
        				Log.d(TAG,"Not Successfull");
        			}
        		}
				
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
                    adapter = new SimpleAdapter(
                            DashBoard.this, noticesList,//
                            R.layout.list_item, new String[] { TAG_ID, TAG_TITLE,
                                    TAG_DESC, TAG_DATE},
                            new int[] { R.id.noticeId, R.id.title, R.id.description, R.id.date });
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
