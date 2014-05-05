package com.example.enoticeapp;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class LandingPage extends Activity{
	Intent myIntent;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_landing);
       
	}
	public void login(View view){
		myIntent = new Intent(this, UserLogin.class);
		startActivity(myIntent);
	}
	public void register(View view){
		myIntent = new Intent(this, Register.class);
		startActivity(myIntent);
	}
}
