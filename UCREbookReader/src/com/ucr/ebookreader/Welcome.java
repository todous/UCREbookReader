package com.ucr.ebookreader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@SuppressLint("NewApi")
public class Welcome extends Activity {
	
	String title = "gibberish";
	
	private static final String MEDIA_MOUNTED = "mounted";
	public final static String EXTRA_FILE = "com.ucr.ebookreader.MESSAGE";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.welcome);
		
		// Retrieve current user from Parse.com
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		// Convert currentUser into String
		String struser = currentUser.getUsername().toString();
		
		// Locate TextView in welcome.xml
		TextView txtuser = (TextView) findViewById(R.id.txtuser);

		// Set the currentUser String into TextView
		txtuser.setText("You are logged in as " + struser);
		
		createButtons();
		Button subscription = (Button) findViewById(R.id.button1);
		subscription.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ParseUser.getCurrentUser().getBoolean("monthlySubscription")) {
			    	Toast.makeText(Welcome.this, "Subscription already purchased!", Toast.LENGTH_SHORT).show();
			    }
				else {
					Intent intent = new Intent(Welcome.this,PurchaseSubscription.class);
					startActivity(intent);
				}
			}
		});
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.welcome, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            openSearch();
	            return true;
	        case R.id.action_logout:
	        	Logout();
	        	return true;
	        case R.id.action_scan:
	    		Intent intent = new Intent(Welcome.this, ScanActivity.class);
	    		//intent.putExtra("lastintent", "welcome");
	    		startActivity(intent);
	            //Scan();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openSearch() {
		Intent intent = new Intent(Welcome.this, SearchActivity.class);
		intent.putExtra("lastintent", "welcome");
		startActivity(intent);
	}
	
	public void Logout() {
		Toast.makeText(Welcome.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
		ParseUser.logOut();
		Intent intent = new Intent(Welcome.this, WelcomeAnon.class);
		startActivity(intent);
		//finish();
	}
	
	public void Scan() {
	    //create a list view to display files
	    ListView listview = new ListView(Welcome.this);  //(ListView) findViewById(R.id.listview);

	    //create arrays to hold files and the names of the files
	    final ArrayList<File> files = new ArrayList<File>();
	    ArrayList<String> list = new ArrayList<String>();
	    
	    //if SD card is mounted search for files and add them to files array
	    if(Environment.getExternalStorageState().equals(MEDIA_MOUNTED))
	    {
	    	File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
	    	files.addAll(searchForFiles(sd));
	    }
	    
	    //transfer the names of the files to the list array
	    for (int i = 0; i < files.size(); ++i) 
	    {
	      list.add(files.get(i).getName());
	    }
	    
	    //set up an adapter so listview is clickable
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
	      {
	    	//get name of file that was clicked
	        String item = (String) parent.getItemAtPosition(position);
	        //files.get(position);
	        
	        if(item.endsWith(".pdf"))
	        {
	            Intent intent = new Intent(Welcome.this, DisplayPdf.class);
	            String fileToRead = files.get(position).getAbsolutePath();
	            intent.putExtra(EXTRA_FILE, fileToRead);
	            startActivity(intent);
	        }
	        
	        if(item.endsWith(".txt"))
	        {
	        	Intent intent = new Intent(Welcome.this, DisplayTxt.class);
		        String fileToRead = files.get(position).getAbsolutePath();
		        intent.putExtra(EXTRA_FILE, fileToRead);
		        startActivity(intent);
	        }
	        
	        if(item.endsWith(".epub"))
	        {
		        Intent intent = new Intent(Welcome.this, DisplayEpubWeb.class);
		        String fileToRead = files.get(position).getAbsolutePath();
		        intent.putExtra(EXTRA_FILE, fileToRead);
		        startActivity(intent);
	        }
	      }

	    });
	    setContentView(listview);
	}
	
	//search SD card for ebook files
	public ArrayList<File> searchForFiles(File f) 
	{
		ArrayList<File> files = new ArrayList<File>();
		
	    if (f.isDirectory()) 
	    {
	        File[] dirs = f.listFiles();
	        if(dirs !=null)
	        {
		        for (int i =0; i < dirs.length; ++i) 
		        {
		        	files.addAll(searchForFiles(dirs[i]));
		        }
	        }
	        
	    } 
	    else 
	    {
	    	String[] ebookExt = new String[] {".pdf",".epub",".txt",".doc"};
	    	for(String iter : ebookExt)
	    	{
	    		if(f.getName().toLowerCase(Locale.getDefault()).endsWith(iter))
	    		{
	    			files.add(f);
	    		    return files;
	    		}
	    	}

	    }
	    return files;
	}
	
	//dynamically create buttons for books
	public void createButtons() {
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> books, ParseException e) {
				
				ParseObject book;
				int lastButtonId = 0;
				
				if (e == null) {
					//iterate through list of books retrieved from server
					for(int i = 0; i < books.size(); i++) {
						//Grab out the title
						book = books.get(i);
						title = book.getString("title");
						final String bookObjId = book.getObjectId();
						
						//Create Button
						Button bookButton = new Button(Welcome.this);
						bookButton.setText(title);
						
						//Set OnClick for the button
						bookButton.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent intent = new Intent(Welcome.this, PickedBook.class);
								intent.putExtra("passedId", bookObjId);
								startActivity(intent);
						    }
						});
						
						//Set Layout Parameters
						RelativeLayout rltemp = (RelativeLayout) findViewById(R.id.welcomeRLayout);
						RelativeLayout.LayoutParams lptemp = new RelativeLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						
						if(i == 0) lptemp.addRule(RelativeLayout.BELOW, R.id.txtuser);
						else lptemp.addRule(RelativeLayout.BELOW, lastButtonId);
						
						//Add Parameters to button
						bookButton.setLayoutParams(lptemp);
						
						//Set the Id (add 1000 to make it unique)
						lastButtonId = 1000 + i;
						bookButton.setId(lastButtonId);
						
						//Add Button to View
						rltemp.addView(bookButton);
					}
				} else {
					// something went wrong
				}
			}
		});
		
	}

}