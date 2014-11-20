package com.ucr.ebookreader;

import java.util.List;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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
	
	ImageButton h,f,r,s,hp,g;
	
	@Override
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
		 
		 h = (ImageButton) findViewById(R.id.imageButton6);
		 f = (ImageButton) findViewById(R.id.imageButton5);
		 r = (ImageButton) findViewById(R.id.imageButton3);
		 s = (ImageButton) findViewById(R.id.imageButton2);
		 hp = (ImageButton) findViewById(R.id.imageButton1);
		 g = (ImageButton) findViewById(R.id.imageButton4);
		 
		 h.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "B0N0U2i8K5");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 f.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "wY9cdmCj41");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 r.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "ASfKn1mj2z");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 s.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "G7i1TxwkQA");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 hp.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "av40lKsrog");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 g.setOnClickListener(new OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
	 
				   Intent intent = new Intent(BrowseActivity.this, PickedBook.class);
				   intent.putExtra("passedId", "KJP2OZRwbY");
				   startActivity(intent);
	 
				}
	 
			});
		 
		 
		 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
                openSearch();
                return true;
	        case R.id.action_shop:
	            openShop();
	            return true;
	        case R.id.action_logout:
	        	Logout();
	        	return true;
	        case R.id.action_scan:
	    		Intent intent = new Intent(BrowseActivity.this, ScanActivity.class);
	    		//intent.putExtra("lastintent", "welcome");
	    		startActivity(intent);
	            //Scan();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openSearch() {
		Intent intent = new Intent(BrowseActivity.this, SearchActivity.class);
		startActivity(intent);
	}
	
	public void openShop() {
		Intent intent = new Intent(BrowseActivity.this, Welcome.class);
		startActivity(intent);
	}
	
	
	public void Logout() {
		Toast.makeText(BrowseActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
		ParseUser.logOut();
		Intent intent = new Intent(BrowseActivity.this, WelcomeAnon.class);
		startActivity(intent);
	}
	

}