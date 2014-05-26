package com.example.enoticeapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
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
		/*ArrayList<Integer>idArray = new ArrayList<Integer>();
		//ArrayAdapter<HashMap<String,String>> adapter;
		public ActionBarCallBack(int count, ArrayList<Integer> idArray){
			this.count=count;
			//this.adapter=adapter;
			this.idArray=idArray;
			
		
		}*/
		ListView lv;
		public ActionBarCallBack(int count,ListView lv){
			this.count=count;
			this.lv=lv;
		}
		  
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        	Log.d("Action button clicked","hello");
        	long [] selected = lv.getCheckedItemIds();
            // TODO Auto-generated method stub
        	int length=selected.length;
        	if(length>0){
        	for(int i=0;i<length;i++)
        	Log.d("Array of ids",""+selected[i]);
        	}
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
