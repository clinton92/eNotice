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

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
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
import android.view.LayoutInflater;
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

public class DashBoard extends Activity implements OnQueryTextListener{
	
	// Progress Dialog
	private ProgressDialog pDialog;
	ListView mListView;
	 MenuItem menuItem;
	 TextView noticeId;
	public ArrayList<Integer> idArray=new ArrayList<Integer>();
	//ListAdapter adapter;
	ArrayAdapter<HashMap<String,String>> adapter;
	CheckBox c;
	// Creating JSON Parser object
	//JSONParser jParser = new JSONParser();
	public static ArrayList<HashMap<String, String>> noticesList = new ArrayList<HashMap<String, String>>();
	public static HashMap<String,String> map;
	// url to get all products list
	private static String url_all_notices = "http://davinder.in/get_notices.php?flag=";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NOTICE = "notice";
	private static final String TAG_NOTICES = "notices";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DATE = "date";
	private static final String TAG_ID = "id";
	private static final String LIST_POSITION = "position";
	private static final String LIST_NUM= "num";
	private static final String TAG_FILEPATH="filepath";
	//private static final String LIST_INSTANCE_STATE = "list_state";
	private static final String TAG_DESC = "description";
	private String title=null;
	private String description=null;
	private Integer id = null;
	private String datetime=null;
	private static int count=0;
	private static String filepath;
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
	ArrayAdapter<HashMap<String,String>> adapter2;
	
	// Hashmap for ListView
	public static HashMap<String,String> getNoticeFromHashMap(int key) {
	        return noticesList.get(key);
	}
	
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int user=-1;
		// Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    Intent myIntent =getIntent();
	    if(myIntent.hasExtra("user_type")){
	    	user=Integer.parseInt(myIntent.getStringExtra("user_type"));
	    	
	    }
	    if(user==2)
	    	{MenuItem mymenu = menu.findItem(R.id.post);
	    	mymenu.setVisible(false);
	    	}
	    SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
	    //lv.setTextFilterEnabled(true);
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setSubmitButtonEnabled(true);
	    searchView.setOnQueryTextListener(this);
	    return super.onCreateOptionsMenu(menu);
	 }
	
	    
	 @Override
	 public boolean onQueryTextChange(String newText){
		 // this is your adapter that will be filtered
		 if (TextUtils.isEmpty(newText)){
			 //adapter.notifyDataSetChanged();
			
	     }
	    
	    return true;
	 }
	    
	 @Override
	 public boolean onQueryTextSubmit(String query) {
	    String searchString=query;
	    int textLength=searchString.length();
	    ArrayList<HashMap<String,String>> originalValues = new ArrayList<HashMap<String,String>>();
	    
	    //Copying the notices arraylist before modifying.
	    for(int i=0;i<noticesList.size();i++)
	        originalValues.add(noticesList.get(i));
	    if(noticesList!=null)
	    	noticesList.clear();
	    	   
	    for(int i=0;i<originalValues.size();i++){
	    	String noticeTitle=originalValues.get(i).get("title").toString();
	    	if(textLength<=noticeTitle.length()){
	    		if(searchString.equalsIgnoreCase(noticeTitle.substring(0,textLength))){
	    			noticesList.add(originalValues.get(i));
	    		}	    		   
	    	}
	    }
	    
	    adapter.notifyDataSetChanged();
	    	   	    	
	    return false;
	 }
	   
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      	case R.id.menu_load:
	      		//Showing progress spinner on refresh in action bar
	      		menuItem = item;
	        	menuItem.setActionView(R.layout.progressbar);
	        	menuItem.expandActionView();
	        	//	Doing refreshing
	        	new LoadAllNotices().execute(flag,null,null);
	        	//task.execute("test");
	        	break;
	      	case R.id.post:
	      		Intent myIntent = new Intent(this,PostNotice.class);
	      		startActivity(myIntent);
	      	default:
	      		break;
	      }
	      return true;
	    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dash_board);
		
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
	        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		
	    // Clearing the previous hashmap
    	if(noticesList!=null)//
    		noticesList.clear();
    	if(map!=null)
    		map.clear();
    	if(count>0)
    		count=0;
    	
    	//Setting flag with last received id.
    	myDb = sqlHelper.getReadableDatabase();
    	myCursor = myDb.rawQuery("SELECT * FROM notices", null);
    	
    	if (myCursor.getCount()!=0){
    		myCursor.moveToFirst();
    		do {
    			//Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2));
    			flag=myCursor.getInt(0);
    		}while(myCursor.moveToNext());
    	  		//Toast.makeText(this, ""+flag, Toast.LENGTH_SHORT).show();
    	}else
    		Log.d("Empty","empty");
    	
    	//Loading notices on creating activity
    	new LoadAllNotices().execute(flag,null,null);
    	
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

        	if(noticesList!=null)//
        		noticesList.clear();
        	if(map!=null)
        		map.clear();
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
            int fl =args[0];
            String uri= url_all_notices+fl;
            Log.d("details",url_all_notices);
           
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
        					filepath =myObj2.getString("filepath");
        					Log.d(TAG, "Description: "+description);
        					datetime = myObj2.getString("date");
        					formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        					try{
        						myDate = formatter.parse(datetime);
        						//if(myDate==new Date())
        							//date = timeFormat.format(myDate.getTime());
        						//else
        							date = dateFormat.format(myDate.getDate());
        						
        						Log.d(date,date);
        					
        					}
        					catch(ParseException e){
        						e.printStackTrace();
        					}
        					
        					// Updating local db
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
            myDb = sqlHelper.getReadableDatabase();
            myCursor = myDb.rawQuery("SELECT * FROM "+OfflineData.table1,null);
            myCursor.moveToFirst();
           
    		do {
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put(TAG_ID, ""+myCursor.getInt(0));
                map.put(TAG_TITLE, myCursor.getString(1));
                map.put(TAG_DESC, myCursor.getString(2));
                map.put(TAG_DATE,myCursor.getString(3));
                map.put(TAG_FILEPATH,myCursor.getString(4));
                noticesList.add(map);//
    			Log.d("Dashboard SQLite", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2)+", "+myCursor.getString(3));
    			flag=myCursor.getInt(0);
    		}while(myCursor.moveToNext());
            
    		adapter=new MyListAdapter(DashBoard.this,noticesList);
        	lv= (ListView) findViewById(R.id.list);
    		lv.setAdapter(adapter);
    		lv.setTextFilterEnabled(true);
    	
        	if(menuItem!=null){
        		menuItem.collapseActionView();
        		menuItem.setActionView(null);
        	}
        	
        	// Setting up onClick Listener
        	lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    		lv.setOnItemClickListener(new OnItemClickListener() {
    			
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                	TextView myId =(TextView) view.findViewById(R.id.noticeId);
                	int myid=Integer.parseInt(myId.getText().toString());
                	Log.d("Id of clicked item",""+myid);
                	//map = noticesList.get(position);
                	if(count>0)
                		count=0;
                	if(mActionMode!=null)
                		mActionMode.finish();
                	

                	SparseBooleanArray pos=lv.getCheckedItemPositions();
                	for(int i=0;i<pos.size();i++)
                	Log.d("Sparse Array",""+pos.get(i));
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
                   in.putExtra(TAG_ID,myid);
                   in.putExtra(LIST_POSITION,position);
                   startActivity(in);
                }
           });
    		
        }
        private void insertNoticeInLocalDB(){
        	myDb = sqlHelper.getWritableDatabase();
        	//myCursor = myDb.rawQuery("INSERT INTO "+OfflineData.table1+"("+OfflineData.title+", "+OfflineData.desc+", "+OfflineData.date+", "+OfflineData.filepath+", "+OfflineData.status+") VALUES('"+ title+"', '"+description+"', '"+date+"', '"+filepath+"', 'N')",null);
    		ContentValues content = new ContentValues();
        	content.put(OfflineData.title, title);
        	content.put(OfflineData.desc, description);
        	content.put(OfflineData.date, date);
        	content.put(OfflineData.filepath, filepath);
        	content.put(OfflineData.status, "N");
        	myDb.insert(OfflineData.table1, null, content);
        	//myDb.close();
        }
 
    }
    
    public class ActionBarCallBack implements ActionMode.Callback {
    	int count;
    	OfflineData sqlHelper = new OfflineData(DashBoard.this, OfflineData.database, null, OfflineData.database_version);
    	
    	public ActionBarCallBack(int count){
    		this.count=count;
    		
    	}
    	  
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        	Log.d("Action button clicked","hello");
        	if(idArray!=null)
        		idArray.clear();
        	
        	for(int i=0;i<lv.getCount();i++){
        		View v = lv.getChildAt(i);
        		if(v!=null){
        			//Log.d("View","Not null");
        			CheckBox cb =(CheckBox) v.findViewById(R.id.checkBox1);
        			TextView tv = (TextView) v.findViewById(R.id.noticeId);
        			
        			if(cb.isChecked()){
        				idArray.add(Integer.parseInt(tv.getText().toString()));
        			}
        		}
        	}
        	for(int i=0;i<idArray.size();i++){
        		Log.d("Checked Ids: ",""+idArray.get(i));
        		myDb = sqlHelper.getWritableDatabase();
        		//myDb.rawQuery("DELETE FROM "+OfflineData.table1+" WHERE id="+idArray.get(i),null);
        		//myDb.delete(OfflineData.table1, id+"="+idArray.get(i), null);
        		myDb.execSQL("delete from "+OfflineData.table1+" where id='"+idArray.get(i)+"'");
        		Log.d("Cursor contains: ",""+myCursor);
        		Log.d("Item deleted",""+idArray.get(i));
        	}
        	mActionMode.finish();
        	if(count>0)
        		count=0;
        	new LoadAllNotices().execute(flag,null,null);
        	
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub

            mode.setTitle(""+count+" Selected");
            return false;
        }
    }
    
 private class MyListAdapter extends ArrayAdapter<HashMap<String,String>>
	{ 
    	Context mContext;
    	
    	ArrayList<HashMap<String,String>> noticesList;
		public MyListAdapter(Context mContext, ArrayList<HashMap<String,String>> noticesList)
		{
			super(DashBoard.this,R.layout.list_item,noticesList);
			this.mContext=mContext;
			this.noticesList=noticesList;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View itemView=convertView;
			//Log.d("View",""+itemView);
			map=noticesList.get(position);
			LayoutInflater mInflater;
		
			mInflater = ((Activity)mContext).getLayoutInflater();
			itemView = mInflater.inflate(R.layout.list_item, parent, false);
			
			TextView tv=(TextView)itemView.findViewById(R.id.title);
			if(tv!=null)
			tv.setText(map.get("title"));
			
			TextView description=(TextView)itemView.findViewById(R.id.description);
			description.setText(map.get("description"));
			
			//TextView date=(TextView)itemView.findViewById(R.id.date);
			//date.setText(map.get("date"));
			
			noticeId=(TextView)itemView.findViewById(R.id.noticeId);
			noticeId.setText(map.get(TAG_ID));
			//lv=getListView();
			CheckBox checkbox =(CheckBox) itemView.findViewById(R.id.checkBox1);
			//if(){
				
			//}
    		if(checkbox!=null){
    			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	  
    				@Override
    				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    					// 	TODO Auto-generated method stub
  
    					if(isChecked){
    						count++;
    						mActionMode = DashBoard.this.startActionMode(new ActionBarCallBack(count));
    					}
    					else{
    						if(count>0){
    							count--;
    							Log.d("Removed id",""+idArray);
    						}
    							
    						if(count>0){
    							mActionMode = DashBoard.this.startActionMode(new ActionBarCallBack(count));}
    						else{
    							mActionMode.finish();
    						}
    					}
    				}
    			
    			});
    		}
    	return itemView;
    }
			
	}
 }	/*
		
		 @Override
         public Filter getFilter() {
             Filter filter = new Filter() {

                 @SuppressWarnings("unchecked")
                 @Override
                 protected void publishResults(CharSequence constraint,FilterResults results) {

                     map = (ArrayList<HashMap<String,String>>) results.values; // has the filtered values
                     notifyDataSetChanged();  // notifies the data with new filtered values
                 }

                 @Override
                 protected FilterResults performFiltering(CharSequence constraint) {
                     FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                     HashMap<String,String> FilteredArrList = new HashMap<String,String>();

                     if (noticesList == null) {
                         mOriginalValues = new HashMap<String,String>(map); // saves the original data in mOriginalValues
                     }

                     /********
                      * 
                      *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                      *  else does the Filtering and returns FilteredArrList(Filtered)  
                      *
                      ********/
           /*          if (constraint == null || constraint.length() == 0) {

                         // set the Original result to return  
                         results.count = mOriginalValues.size();
                         results.values = mOriginalValues;
                     } else {
                         constraint = constraint.toString().toLowerCase();
                         for (int i = 0; i < mOriginalValues.size(); i++) {
                             String data = mOriginalValues.get(i);
                             if (data.toLowerCase().startsWith(constraint.toString())) {
                                 FilteredArrList.add(data);
                             }
                         }
                         // set the Filtered result to return
                         results.count = FilteredArrList.size();
                         results.values = FilteredArrList;
                     }
                     return results;
                 }
             };
             return filter;
         }*/
		


