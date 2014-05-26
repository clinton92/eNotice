package com.example.enoticeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;

import com.example.enoticeapp.SimpleGestureFilter.SimpleGestureListener;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

public class ShowNotice extends Activity implements SimpleGestureListener{
	TextView tv1,tv2;
	private SimpleGestureFilter detector;
	 Bitmap bitmap = null;
     //InputStream in = null; 
	public static int position;
	public static int num;
	private Uri uri;
	private String filepath;
	private String filephonepath;
	Intent myIntent;
	SQLiteDatabase myDb;
	ImageView image;
	// Progress Dialog
	private ProgressDialog pDialog;
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
		image = (ImageView) findViewById(R.id.imageView1);
				
		Intent myIntent = getIntent();
		if(myIntent.hasExtra("id")){
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
		filepath= myCursor.getString(4);
		Log.d("Filepath for downloading",""+filepath);
		if(filepath!=null){
			new DownloadImage().execute(filepath);
			 
		}
		
	}
	
	private class DownloadImage extends AsyncTask<String,Void,Bitmap>{
		
		@Override
		public void onPreExecute(){
			
		}
		
		@Override
		public Bitmap doInBackground(String ...args){
			String URL=args[0];
			Bitmap bitmap = null;
	        InputStream in = null;        
	        try {
	            in = OpenHttpConnection(URL);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
			
			return bitmap;
		}
		
		@Override
		public void onPostExecute(Bitmap bmp){
			image = (ImageView)findViewById(R.id.imageView1);
			image.setImageBitmap(bmp);
			image.setVisibility(View.VISIBLE);
			//startScanner();
			//pDialog.dismiss();	
		}
		
		private InputStream OpenHttpConnection(String urlString) 
			    throws IOException
			    {
			        InputStream in = null;
			        int response = -1;
			               
			        URL url = new URL(urlString); 
			        URLConnection conn = url.openConnection();
			                 
			        if (!(conn instanceof HttpURLConnection))                     
			            throw new IOException("Not an HTTP connection");
			        
			        try{
			            HttpURLConnection httpConn = (HttpURLConnection) conn;
			            httpConn.setAllowUserInteraction(false);
			            httpConn.setInstanceFollowRedirects(true);
			            httpConn.setRequestMethod("GET");
			            httpConn.connect(); 

			            response = httpConn.getResponseCode();                 
			            if (response == HttpURLConnection.HTTP_OK) {
			                in = httpConn.getInputStream();                                 
			            }                     
			        }
			        catch (Exception ex)
			        {
			            throw new IOException("Error connecting");            
			        }
			        return in;     
			    }
		
	}
	
	
	
/*	public void downloadFile(){
		/*ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		//imgLoader.DisplayImage(filepath, loader, image);
		 pDialog = new ProgressDialog(ShowNotice.this);
         pDialog.setMessage("Loading notices. Please wait...");
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
         
         new Thread(new Runnable(){
				@Override
				public void run(){
					 Intent intent = new Intent(ShowNotice.this, DownloadService.class);
				 	    // Create a new Messenger for the communication back
				 	    Messenger messenger = new Messenger(handler);
				 	    intent.putExtra("MESSENGER", messenger);
				 	    intent.setData(Uri.parse(filepath));
				 	    intent.putExtra("urlpath", filepath);
				 	    startService(intent);
				 	    
		//		 	   pDialog.dismiss();
				}
		}).start();
        
       
	}
	
	private Handler handler = new Handler() {
	    public void handleMessage(Message message) {
	      Object path = message.obj;
	      if (message.arg1 == RESULT_OK && path != null) {
	    	  Log.d("Downloaded",path.toString());
	        Toast.makeText(ShowNotice.this,
	            "Downloaded" + path.toString(), Toast.LENGTH_LONG).show();
	        filephonepath= path.toString();
	        
	        if(filephonepath!=null){
	        	startScanner();
	        	/*File f = new File(filephonepath);
	        	
				Uri uri =Uri.fromFile(f);//filephonepath;
				Log.d("Phone path",""+uri);*/
	      /*  	if(uri!=null){
	        		image.setImageURI(uri);
	        		image.setVisibility(View.VISIBLE);
	        	}
			}
	        pDialog.dismiss();
	      } else {
	        Toast.makeText(ShowNotice.this, "Download failed.",Toast.LENGTH_LONG).show();
	      }
	    };
	  };
	 */ 
	  private void startScanner() {
	    	Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    	File f = new File(filephonepath);
	    	uri = Uri.fromFile(f);
	    	//fName=fileUri.getLastPathSegment();
	    	//Log.d("Simple FileName",fName);
	    	Log.d("link for gallery",""+uri);
	    	scanIntent.setData(uri);
	    	this.sendBroadcast(scanIntent);
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
