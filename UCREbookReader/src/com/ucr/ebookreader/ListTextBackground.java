package com.ucr.ebookreader;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListTextBackground extends Activity {
	
    public static final String BACKGROUND_COLOR = "BACKGROUND_COLOR";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
		ListView listview = new ListView(ListTextBackground.this);
		
		ArrayList<String> list = new ArrayList<String>();
		
		//Black, DarkGrey, White, Blue, 
	    String COLORS[] = {
	    	"black", "darkgray", "grey", "white", "maroon", "navy", "olive"
	    };
	    
	    for(String str: COLORS)
	    {
	    	list.add(str);
	    	//Toast.makeText(this, Integer.valueOf(Color.parseColor("0xff808000")), Toast.LENGTH_SHORT);
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
		    intent.putExtra(BACKGROUND_COLOR, item);
		    setResult(RESULT_OK, intent);
		    finish();
			  
	      }
	    });
	    setContentView(listview);
	}
	

}
