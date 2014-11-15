package com.ucr.ebookreader;

import java.util.List;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseUser;

public class BrowseActivity extends Activity 
{
	TextView haunted;
	TextView flint;
	TextView reckless;
	TextView storm;
	TextView harry;
	TextView global;
	
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.browse);
		
		 haunted = (TextView) findViewById(R.id.hauntedway);
		 flint = (TextView) findViewById(R.id.flintlord);
		 reckless = (TextView) findViewById(R.id.reckless);
		 storm = (TextView) findViewById(R.id.storm);
		 harry = (TextView) findViewById(R.id.harrypotter);
		 global = (TextView) findViewById(R.id.global);
		 
		 haunted.append("Title: The Haunted Way\n" +
				        "Author: Neil Wesson\n" +
				 		"Price: $2\n");
		 flint.append("Title: The Flint Lord\n" +
			        "Author: Richard Herley\n" +
				 	"Price: $2\n");
		 reckless.append("Title: Reckless Viscount\n" +
			        "Author: Amy Sandas\n" +
			        "Price: $2\n");
		 storm.append("Title: Let the Storms Break\n" +
			        "Author: Shannon Messenger\n" +
			        "Price: $1\n");
		 harry.append("Title: Harry Potter and the Sorcerer's Stone\n" +
			        "Author: J.K. Rowling\n" +
			        "Price: $1\n");
		 global.append("Title: Global Forest Fragmentation\n" +
			        "Author: Chris J. Kettle\n" +
			        "Price: $5\n");	 
		 
		 
	}

}