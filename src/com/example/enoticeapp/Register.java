package com.example.enoticeapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

public class Register extends Activity{
	EditText user,pass;
	TextView userMsg, passMsg;
	Validator obj;
	boolean validPass,validUser;
	ProgressDialog progressBar;
	//AlertDialog.Builder alertBuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		user = (EditText)findViewById(R.id.user);
		pass = (EditText)findViewById(R.id.pass);
		userMsg = (TextView)findViewById(R.id.usermsg);
		passMsg = (TextView)findViewById(R.id.passmsg);
		
	}
	
	public void registerUser(View view){
		obj = new Validator();
		validPass = obj.validatePass(pass.getText().toString());
		validUser = obj.validateUser(user.getText().toString());
		if(!validUser)
			new AlertDialog.Builder(this).setTitle("UserName").setMessage("Please enter a valid username").setNeutralButton("Close", null).show();
			//userMsg.setText("Please enter a valid username");
		if(!validPass)
			new AlertDialog.Builder(this).setTitle("Password").setMessage("Please enter a valid password.").setNeutralButton("Close", null).show();
			//passMsg.setText("Please enter a valid password");
		if(validPass && validUser){
		
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
	        //progressBar.setTitle("Registering...");
			//progressBar.setMessage("Please wait.");
	        progressBar.show();
	       
        }
        
        @Override
        protected Void doInBackground(String...arg) {
            
            //Added sleep so that you can see Hello from onPreExecute and after that Inside doInBackground clearly.
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
           
        }
    }

}
