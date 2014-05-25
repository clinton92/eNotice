package com.example.enoticeapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
//import com.example.enoticeapp.DashBoard.idArray;

public class ActionBarCallBack implements ActionMode.Callback {
		int count;
		ArrayList<Integer>idArray = new ArrayList<Integer>();
		//ArrayAdapter<HashMap<String,String>> adapter;
		public ActionBarCallBack(int count, ArrayList<Integer> idArray){
			this.count=count;
			//this.adapter=adapter;
			this.idArray=idArray;
			
		
		}
		  
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // TODO Auto-generated method stub
        	/*for(int i=0;i<lv.getCount();i++)
        	Log.d("Array of ids",""+idArray.get(i));
        	*/
        	//Log.d("Adapter",""+adapter);
        	//ListView lv=getListView();
        	//lv.setAdapter(adapter);
        	
        	/*for(int i=0;i<adapter.getCount();i++){
        		//Log.d("Hello","Hello");
        		View myView=adapter.getView(i,null,null);
        		
        		Log.d("myView",""+myView);
        		CheckBox cb=(CheckBox) myView.findViewById(R.id.checkBox1);
        		TextView tv=(TextView) myView.findViewById(R.id.noticeId);
        		Log.d("Notice id",tv.getText().toString());
        		Log.d("checkbox",""+cb.isChecked());
        		if(cb.isChecked()){
        			idArray.add(Integer.parseInt(tv.getText().toString()));
        			Log.d("Arrayyyyyyyyyy",""+idArray.get(i));
        		}
        		
        	}*/
            return false;
        }
  
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            return true;
        }
  
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // TODO Auto-generated method stub
  
        }
  
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
  
            mode.setTitle(""+count+" Selected");
            return false;
        }
}
