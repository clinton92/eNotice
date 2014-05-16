package com.example.enoticeapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
//import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

public class Register extends Activity{
	EditText user,pass,first,last,email,mobile;
	TextView userMsg, passMsg;
	Validator obj;
	boolean validPass,validUser,validFirst,validLast,validMob,validEmail;
	ProgressDialog progressBar;
	String str;
	//AlertDialog.Builder alertBuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		user = (EditText)findViewById(R.id.user);
		pass = (EditText)findViewById(R.id.pass);
		first = (EditText) findViewById(R.id.first);
		last = (EditText) findViewById(R.id.last);
		email =(EditText) findViewById(R.id.email);
		mobile = (EditText) findViewById(R.id.mob);
		
	}
	
	public void registerUser(View view){
		obj = new Validator();
		validPass = obj.validatePass(pass.getText().toString());
		validUser = obj.validateUser(user.getText().toString());
		validFirst = obj.validateName(first.getText().toString());
		validLast =obj.validateName(last.getText().toString());
		validEmail = obj.validateEmail(email.getText().toString());
		validMob = obj.validateMobile(mobile.getText().toString());
		if(!validUser){
			str = "Please enter a valid username.";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
			
		}
		if(!validPass){
			
			str = "Please enter a valid password";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}
		if(!validEmail){
			str = "Please enter a valid Email address.";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}
		
		if(!validMob){
			str = "Please enter a valid Mobile Number.";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}
		if(!validFirst){
			str = "Please enter a valid First name.";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}
		if(!validLast){
			str = "Please enter a valid  Last name.";
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
			
		}
		if(validPass && validUser && validFirst && validLast && validEmail && validMob){
		
			new RegisteringUser().execute("Hello");
		}
			
		
		
	}
	private class RegisteringUser extends AsyncTask<String, Integer, Void> {
        String mTAG = "myAsyncTask";
        private ProgressDialog progressBar;
        
        @Override
        protected void onPreExecute() {
            Log.d(mTAG, "Hello from onPreExecute");
            progressBar = new ProgressDialog(Register.this);
            progressBar.setCancelable(true);
	        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressBar.setIndeterminate(true);
	        progressBar.setTitle("Registering...");
			progressBar.setMessage("Please wait.");
	        progressBar.show();
	       
        }
        
        @Override
        protected Void doInBackground(String...arg) {
            
            try{
                Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                
                e.printStackTrace();
            }
            
           
            return null;
        }

        @Override
        protected void onPostExecute(Void a) {
            Log.d(mTAG, "Inside onPostExecute");
            progressBar.dismiss();
            str = "You are registered now";
			Toast.makeText(Register.this, str, Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(Register.this,UserLogin.class);
			startActivity(myIntent);
           
        }
    }

}
