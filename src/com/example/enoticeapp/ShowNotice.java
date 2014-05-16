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

import java.util.HashMap;

public class ShowNotice extends Activity implements SimpleGestureListener{
	TextView tv1,tv2;
	private SimpleGestureFilter detector;
	public static int position;
	public static int num;
	Intent myIntent;
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
		if(myIntent.hasExtra("title")){
			tv1.setText(myIntent.getStringExtra("title"));
		}
		if(myIntent.hasExtra("description")){
			tv2.setText(myIntent.getStringExtra("description"));
		}
		
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
