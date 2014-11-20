package com.ucr.ebookreader;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SearchActivity extends Activity 
{
	String search_text;
	EditText search;
	Button search_button;
	Button browse_button;
	Button buybook;
	
	String title;
	String author;
	String genre;
	
	ImageButton hp;
	ImageButton fl;
	ImageButton ltsb;
	ImageButton rv;
	ImageButton gf;
	ImageButton thw;
	
	TextView book_txt;
	
	ParseObject book;
	String lastintent;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//Get the layout from the search.xml
		setContentView(R.layout.search);
		/*
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			lastintent = extras.getString("lastintent");
		}
		*/
		
		search = (EditText) findViewById(R.id.searchtext);
		search_button = (Button) findViewById(R.id.search);
		browse_button = (Button) findViewById(R.id.browse);
		book_txt = (TextView) findViewById(R.id.txtbook);

			
		//setting Book images to invisible
		hp = (ImageButton) findViewById(R.id.imageButton1);
		hp.setVisibility(View.GONE);
		fl = (ImageButton) findViewById(R.id.imageButton5);
		fl.setVisibility(View.GONE);
		ltsb = (ImageButton) findViewById(R.id.imageButton2);
		ltsb.setVisibility(View.GONE);
		rv = (ImageButton) findViewById(R.id.imageButton3);
		rv.setVisibility(View.GONE);
		gf = (ImageButton) findViewById(R.id.imageButton4);
		gf.setVisibility(View.GONE);
		thw = (ImageButton) findViewById(R.id.imageButton6);
		thw.setVisibility(View.GONE);
		
		search_button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) 
			{
				//resets visibility of book images after each click
				hp.setVisibility(View.GONE);
				thw.setVisibility(View.GONE);
			    fl.setVisibility(View.GONE);
				rv.setVisibility(View.GONE);
				ltsb.setVisibility(View.GONE);
				gf.setVisibility(View.GONE);
			 
			    book_txt.setText("");
				
				// Retrieve the text entered from the EditText
				search_text = search.getText().toString();
				
				//Access Parse for list of books online
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
				query.findInBackground(new FindCallback<ParseObject>() {
				   @Override
				   public void done(List<ParseObject> books, ParseException e) {
				         if (e == null) {
				        	 for (int i = 0; i < books.size(); i++) {
				        		 title = books.get(i).getString("title");
				        		 author = books.get(i).getString("author");
				        		 genre = books.get(i).getString("genre");
				        		 
				        		 if ( title.toLowerCase().contains(search_text.toLowerCase())
				     					|| author.toLowerCase().contains(search_text.toLowerCase())
				     					|| genre.toLowerCase().contains(search_text.toLowerCase()) ) {
				     					
				        			 if (title.contains("Harry Potter"))
				        				 hp.setVisibility(View.VISIBLE);
				        			 else if (title.contains("Haunted"))
				        				 thw.setVisibility(View.VISIBLE);
				        			 else if (title.contains("Flint"))
				        				 fl.setVisibility(View.VISIBLE);
				        			 else if (title.contains("Reckless"))
				        				 rv.setVisibility(View.VISIBLE);
				        			 else if (title.contains("Storms"))
				        				 ltsb.setVisibility(View.VISIBLE);
				        			 else if (title.contains("Global"))
				        				 gf.setVisibility(View.VISIBLE);
				        			 
				        			 book_txt.append("Title: " + title + "\n" +
				        					 		 "Author: " + author + "\n" +
				        					 		 "Genre: " + genre + "\n");
				        			 
				     					
				     					Toast.makeText(
				     					getApplicationContext(),
				     					"We got it!",
				     					Toast.LENGTH_LONG).show();
				     					
				     					book = books.get(i);
				     					final String bookObjId = book.getObjectId();
				     					
				     					Button buybook = new Button(SearchActivity.this);
										buybook.setText(title);
				     					
				     					buybook.setOnClickListener(new OnClickListener() {
											public void onClick(View v) {
												Intent intent = new Intent(SearchActivity.this, PickedBook.class);
												intent.putExtra("passedId", bookObjId);
												startActivity(intent);
										    }
										});
				     					
				     					//Set Layout Parameters
										LinearLayout rltemp = (LinearLayout) findViewById(R.id.searchlayout);
										LinearLayout.LayoutParams lptemp = new LinearLayout.LayoutParams(
												LayoutParams.MATCH_PARENT,
												LayoutParams.WRAP_CONTENT);
					
										
										buybook.setLayoutParams(lptemp);
										buybook.setId(1000 + i);
										rltemp.addView(buybook);
									
										
				                 }
				        		 
				        	 }
				        	 if (book_txt.getText().equals("")) {
		        				 Toast.makeText(
					     		 getApplicationContext(),
					   			 "No Results Found.",
					     		 Toast.LENGTH_LONG).show();
		        			 }
				        	 
				         }
				     }
				 });
				
			}
		});	
		
		browse_button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(SearchActivity.this, BrowseActivity.class);
				startActivity(intent);
			}
		});	
		
	}
	
/*	@Override  
	public void onBackPressed() {
	    super.onBackPressed(); 
	    if (lastintent.equals("welcome")){
	    	Intent intent = new Intent(SearchActivity.this, Welcome.class);
	    	startActivity(intent);
	    	//finish();
	    }
	    else {
	    	Intent intent = new Intent(SearchActivity.this, WelcomeAnon.class);
	    	startActivity(intent);
	    	//finish();
	    }
	}
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_shop:
	            openShop();
	            return true;
	        case R.id.action_logout:
	        	Logout();
	        	return true;
	        case R.id.action_scan:
	    		Intent intent = new Intent(SearchActivity.this, ScanActivity.class);
	    		//intent.putExtra("lastintent", "welcome");
	    		startActivity(intent);
	            //Scan();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openShop() {
		Intent intent = new Intent(SearchActivity.this, Welcome.class);
		startActivity(intent);
	}
	
	
	public void Logout() {
		Toast.makeText(SearchActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
		ParseUser.logOut();
		Intent intent = new Intent(SearchActivity.this, WelcomeAnon.class);
		startActivity(intent);
	}
}