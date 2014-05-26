package com.example.enoticeapp;

import java.util.ArrayList;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


public class ActionBarCallBack implements ActionMode.Callback {
	int count;
	public ArrayList<Integer> idArray=new ArrayList<Integer>();
	ListView lv;
	public ActionBarCallBack(int count,ListView lv){
		this.count=count;
		this.lv=lv;
	}
	  
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    	Log.d("Action button clicked","hello");
    	if(idArray!=null)
    		idArray.clear();
    	
    	for(int i=0;i<lv.getCount();i++){
    		View v = lv.getChildAt(i);
    		if(v!=null){
    			Log.d("View","Not null");
    			CheckBox cb =(CheckBox) v.findViewById(R.id.checkBox1);
    			TextView tv = (TextView) v.findViewById(R.id.noticeId);
    			
    			if(cb.isChecked()){
    				idArray.add(Integer.parseInt(tv.getText().toString()));
    			}
    		}
    	}
    	for(int i=0;i<idArray.size();i++)
    		Log.d("Checked Ids: ",""+idArray.get(i));
    	
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