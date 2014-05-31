package com.example.enoticeapp;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enoticeapp.Validator;

public class ForgetPassword extends Activity {
	EditText et=null;
	EditText et1=null;
	String user=null,pass=null;
	Validator obj = new Validator();
	JSONObject json=null;
	String jsonData=null;
	private String password=null;
	ProgressDialog pDialog=null;
	
	@Override
	public void onCreate(Bundle x){
		super.onCreate(x);
		setContentView(R.layout.layout_forgotpass);
		
		et= (EditText) findViewById(R.id.editText1);
		et1= (EditText) findViewById(R.id.editText2);
		
	}
	public void getPassword(View view){
		user = et.getText().toString();
		pass = et1.getText().toString();
		Log.d("Username",""+user);
		Log.d("Password",""+pass);
		
		
		
		if(user.length()>0 && pass.length()>0){
			
			//if(obj.validatePass(pass)){
				new FetchPassword().execute(pass,null,null);
			//}
				//else
					//Toast.makeText(this, "Please enter a valid password",Toast.LENGTH_LONG).show();
		}
					
	}
	
	private class FetchPassword extends AsyncTask<String,Void,String>{
		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();

	            pDialog = new ProgressDialog(ForgetPassword.this);
	            pDialog.setMessage("Resetting Password. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
		 }
		
		@Override
		public String doInBackground(String ...args){

			String url = "http://davinder.in/reset.php";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password", pass));
            params.add(new BasicNameValuePair("username",user));
            
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {//
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            try {//
            	Log.d("APAchEEE","Running MySQL query");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				jsonData = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
           		Log.d("response", jsonData);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
            try{
            	json = new JSONObject(jsonData);
            	int success = json.getInt("success");
            	if(success==1){
            		//password=json.getString("password");
            		runOnUiThread(new Runnable(){
            			@Override
            			public void run(){
            				Toast.makeText(ForgetPassword.this, "Password Updated.", Toast.LENGTH_SHORT).show();
            			}
            		});
            	}
            	else{
            		runOnUiThread(new Runnable(){
            			@Override
            			public void run(){
            				Toast.makeText(ForgetPassword.this, "This username is not registered", Toast.LENGTH_SHORT).show();
            			}
            		});
            		
            	}
    		
            }
            catch(JSONException e){
            	e.printStackTrace();
            }
			
			return password;
			
		}
		
		@Override
		public void onPostExecute(String password){
			pDialog.dismiss();
			/*String message = "Your password is: ";
			message+=password;
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phone, null, message, null, null);
			*/
			
		}
	}

}
