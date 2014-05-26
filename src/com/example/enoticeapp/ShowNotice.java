package com.example.enoticeapp;

import android.app.ActionBar;
import android.app.Activity;

import com.example.enoticeapp.SimpleGestureFilter.SimpleGestureListener;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.HashMap;

public class ShowNotice extends Activity implements SimpleGestureListener{
	TextView tv1,tv2;
	private SimpleGestureFilter detector;
	public static int position;
	public static int num;
	Intent myIntent;
	SQLiteDatabase myDb;
	OfflineData sqlHelper = new OfflineData(this, OfflineData.database, null, OfflineData.database_version);
	Cursor myCursor;
	HashMap<String,String> map;
	//DashBoard dashObj = new DashBoard();
	//klkl

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_shownotice);
        // get action bar  
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
		detector = new SimpleGestureFilter(this,this);
        
		tv1 = (TextView)findViewById(R.id.textView1);
		tv2 = (TextView)findViewById(R.id.textView2);
		Intent myIntent = getIntent();
		/*if(myIntent.hasExtra("title")){
			tv1.setText(myIntent.getStringExtra("title"));
		}
		if(myIntent.hasExtra("description")){
			tv2.setText(myIntent.getStringExtra("description"));
		}*/
		if(myIntent.hasExtra("id")){
			//int id = myIntent.getIntExtra("TAG_ID",);
			int id=myIntent.getIntExtra("id", 0);
			Log.d("Received in Intent",""+id);
			getDataFromLocalDB(id);
		}
		
	}
	public void getDataFromLocalDB(int id){
		myDb = sqlHelper.getReadableDatabase();
		Log.d("Received Id in Local db function",""+id);
		myCursor = myDb.rawQuery("SELECT * FROM notices where id="+id, null);
		myCursor.moveToFirst();
		Log.d("title",myCursor.getString(1));
		tv1.setText(myCursor.getString(1));
		Log.d("desc",myCursor.getString(2));
		tv2.setText(myCursor.getString(2));
		String filepath= myCursor.getString(4);
		if(filepath!=null){
			new Thread(new Runnable(
					@Override
					public void run(){
						downloadFile();
					}
			)).start();
			
			
			
		}
		/*
		myDb = sqlHelper.getReadableDatabase();
		myCursor = myDb.rawQuery("SELECT * FROM notices", null);*//*
    	if (myCursor.getCount()!=0){
    		myCursor.moveToFirst();
    		do {
    			Log.d("Dashboard shownotice", myCursor.getInt(0)+", "+myCursor.getString(1)+", "+myCursor.getString(2));
    			tv1.setText(myCursor.getString(1));
    	//		flag=myCursor.getInt(0);
    		}while(myCursor.moveToNext());
    	  		
    	}else
    		Log.d("Empty","empty");
    		*/
    	
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
	
	 @Override
     public void onSwipe(int direction) {
      String str = "";
      switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT : 
            	str = "Swipe Right";
            	myIntent = getIntent();
            	myIntent.getIntExtra("position",position);
            	Log.d("position right swipe",""+position);
            	if(position>0)
            	position--;
            	nextActivity(position);
            	break;
            	
            
            case SimpleGestureFilter.SWIPE_LEFT :  
            	str = "Swipe Left";
            	myIntent = getIntent();
            	myIntent.getIntExtra("position",position);
            	myIntent.getIntExtra("num", num);
            	Log.d("position left swipe",""+position);
            	if(position<=num)
            	position++;
            	Log.d("position left swipe after increment",""+position);
            	nextActivity(position);
            	break;
            
            case SimpleGestureFilter.SWIPE_DOWN :  
    	  		str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :
    	  		str = "Swipe Up";
                break;
      
      }
       Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
     }
      
	 
	 public void nextActivity(int position){
		 //if(position>=0){
		 
	      map = DashBoard.getNoticeFromHashMap(position);
	      myIntent.putExtra("title", map.get("title"));
	      myIntent.putExtra("description",map.get("description"));
	      myIntent.putExtra("position", position);
	      finish();
	      startActivity(myIntent);
		//}
	 }
     @Override
     public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
     }
     
     /* For Up navigation i.e switching to the parent activity on swiping*/
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
         // Respond to the action bar's Up/Home button
         case android.R.id.home:
             NavUtils.navigateUpFromSameTask(this);
             return true;
         }
         return super.onOptionsItemSelected(item);
     }

}
