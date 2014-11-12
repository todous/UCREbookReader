package com.ucr.ebookreader;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageButton;

import com.parse.LogInCallback;
import com.parse.FindCallback;
import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import com.parse.ParseException;

public class SearchActivity extends Activity 
{
	String search_text;
	EditText search;
	Button search_button;
	Button browse_button;
	
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
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//Get the layout from the search.xml
		setContentView(R.layout.search);
		
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
				        		 
				                 }
				        		 
				        	 }
				        	 if (book_txt.getText().equals("")) {
		        				 Toast.makeText(
					     		 getApplicationContext(),
					   			 "No Results Found.",
					     		 Toast.LENGTH_LONG).show();
		        			 }
				        	 
				         }
				        else {
				             //failed
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
				finish();
			}
		});	
		
	}	
}