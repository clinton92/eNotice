package com.example.enoticeapp;

//import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
//import android.widget.TextView;



public class DashBoard extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dash_board);
		/*Intent myIntent = getIntent();
		if (myIntent.hasExtra("myExtra")){
			TextView mText = (TextView)findViewById(R.id.textView1);
			mText.setText("Welcome "+myIntent.getStringExtra("myExtra")+"!");
		}*/
	}

}
