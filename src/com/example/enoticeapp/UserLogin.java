package com.example.enoticeapp;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.Activity;
//import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
//import java.util.regex.Pattern;
public class UserLogin extends Activity {
	
	 HttpPost httppost;
	    StringBuffer buffer;
	    HttpResponse response;
	    HttpClient httpclient;
	    List<NameValuePair> nameValuePairs;
	    Button b;
	    EditText user;
	    EditText pass;
	   /* private static final String PASSWORD_PATTERN ="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	    private Pattern pattern1,pattern2;*/
		//private String response2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_login);
        //b = (Button)findViewById(R.id.button1); 
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        /*pattern1 = Pattern.compile(PASSWORD_PATTERN);
        pattern2 = Pattern.compile(USERNAME_PATTERN);*/
       // TextView tv = (TextView)findViewById(R.id.tv);
         
    }
   /* public void login(View view){
    	EditText mEditText = (EditText)findViewById(R.id.editText1);
    	String str = mEditText.getText().toString();
       	Intent myIntent = new Intent(this, DashBoard.class);
    	myIntent.putExtra("myExtra", str);
    	startActivity(myIntent);
    	
    }*/

    public void login(View view){
    	//validate(user.getText().toString(),pass.getText().toString());
       new Thread(){
    	   public void run(){
    		   try{           
    	 
              
    			   DefaultHttpClient httpclient = new DefaultHttpClient();
    			   HttpPost httppost = new HttpPost("http://192.168.0.117/login.php"); // make sure the url is correct.
    			   //	add your data
    			   Log.d("myyy","connnnnnnn");
    			   nameValuePairs = new ArrayList<NameValuePair>(2);
    			   // 	Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
    			   nameValuePairs.add(new BasicNameValuePair("username",user.getText().toString()));  // $Edittext_value = $_POST['Edittext_value'];
    			   nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString()));
    			   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			   //	Execute HTTP Post Request
    			   response=httpclient.execute(httppost);
            
    			   BufferedReader br = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
    			   String line = "";
    			   while ((line = br.readLine()) != null)
    			   {
    				   Log.d("mytag", line);
    				   
    			   }
             
    		   }catch(Exception e){
            //dialog.dismiss();
    			   System.out.println("Exception : ");
    			   e.printStackTrace();
    		   }
    	   }
       }.start();
    }
    
    /*public void validate(String user, String pass){
    	matcher1 = pattern.matcher(username);
    	matcher2 = 
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_login, menu);
        return true;
    }
    
}
