package com.example.enoticeapp;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;






//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.util.*;

public class UserLogin extends Activity {
	
	 HttpPost httppost;
	 StringBuffer buffer;
	 HttpResponse response;
	 HttpClient httpclient;
	 List<NameValuePair> nameValuePairs;
	 Button b;
	 EditText user;
	 EditText pass;
	 private static final String TAG = "Login";
	 String jsonData;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_login);
        
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
       
         
    }
 
    public void login(View view){
    	
       new Thread(){
    	   public void run(){
    		   try{           
    	 
              
    			   DefaultHttpClient httpclient = new DefaultHttpClient();
    			   HttpPost httppost = new HttpPost("http://192.168.43.165/login.php"); // make sure the url is correct.
    			  
    			   Log.d(TAG,"Connection Done!");
    			   //	add your data
    			   nameValuePairs = new ArrayList<NameValuePair>(2);
    			   
    			   nameValuePairs.add(new BasicNameValuePair("username",user.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
    			   nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString().trim()));
    			   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			   //	Execute HTTP Post Request
    			   response = httpclient.execute(httppost);
    			   jsonData = EntityUtils.toString(response.getEntity(), "UTF-8");
               		Log.d("response", jsonData);
            
    			   JSONObject json = null;
    			   JSONArray myArray=null;
    			   int success =-1;
    			   int user_type=-1;
    			   
    		       String JSON_TAG = "JSON Parse";
    		    	try {
    		    		json = new JSONObject(jsonData);
    		    		success = json.getInt("success");
    		    		
    		    		if(success==1){
    		    			myArray = json.getJSONArray("user_data");
    		    			JSONObject obj = myArray.getJSONObject(0);
    		    			user_type= obj.getInt("user_type");
    		    			if(user_type==2){
    		    				Intent myIntent = new Intent(UserLogin.this,DashBoard.class);
    		    				startActivity(myIntent);
    		    			}
    		    		}
    		    	}
    		    	catch(JSONException e){
    		    		Log.d(JSON_TAG,"Exception: ");
    		    		e.printStackTrace();
    		    		
    		    	}
    		       /*BufferedReader br = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
    			   String line = "";
    			   while ((line = br.readLine()) != null)
    			   {
    				   Log.d("mytag", line);
    				   //
    			   }*/
             
    		   }catch(Exception e){
    			   System.out.println("Exception : ");
    			   e.printStackTrace();
    		   }
    	   }
       }.start();
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_login, menu);
        return true;
    }
    
}
