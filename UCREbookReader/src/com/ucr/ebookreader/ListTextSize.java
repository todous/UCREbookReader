package com.ucr.ebookreader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListTextSize extends Activity {
	
    public static final String TEXT_SIZE = "TEXT_SIZE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
		ListView listview = new ListView(ListTextSize.this);
		
		ArrayList<String> list = new ArrayList<String>();
		Integer x;
		for(int i = 1; i < 18; i++)
		{
			x = Integer.valueOf(i*2);
			list.add(x.toString());
		}
		
	    //set up an adapter so listview is clickable
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	    {
	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
	      {
			String item = (String) parent.getItemAtPosition(position);
		    Intent intent = new Intent();
		    intent.putExtra(TEXT_SIZE, item);
		    setResult(RESULT_OK, intent);
		    finish();
			  
	      }
	    });
	    setContentView(listview);
	}
	

}
