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
	String phone=null;
	Validator obj = new Validator();
	JSONObject json=null;
	String jsonData=null;
	private String password=null;
	
	@Override
	public void onCreate(Bundle x){
		super.onCreate(x);
		setContentView(R.layout.layout_forgotpass);
		
		et= (EditText) findViewById(R.id.editText1);
		
	}
	public void getPassword(View view){
		phone = et.getText().toString();
		Log.d("Phone number",""+phone);
		
		
		
		if(phone.length()>0){
			
			if(obj.validateMobile(phone)){
				new FetchPassword().execute(phone,null,null);
			}
		}
					
	}
	
	private class FetchPassword extends AsyncTask<String,Void,String>{
		
		@Override
		public String doInBackground(String ...args){

			String url = "http://192.168.43.165/fetch_password.php";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phone", phone));
            
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
            		password=json.getString("password");
            	}
            	else{
            		runOnUiThread(new Runnable(){
            			@Override
            			public void run(){
            				Toast.makeText(ForgetPassword.this, "This number is not registered", Toast.LENGTH_SHORT).show();
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
			String message = "Your password is: ";
			message+=password;
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phone, null, message, null, null);
			
			
		}
	}

}
