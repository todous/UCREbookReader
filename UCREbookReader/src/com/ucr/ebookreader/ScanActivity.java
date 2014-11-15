package com.ucr.ebookreader;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScanActivity extends Activity {
	private static final String MEDIA_MOUNTED = "mounted";
	public final static String EXTRA_FILE = "com.ucr.ebookreader.MESSAGE";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    //create a list view to display files
	    ListView listview = new ListView(ScanActivity.this);  //(ListView) findViewById(R.id.listview);

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
	            Intent intent = new Intent(ScanActivity.this, DisplayPdf.class);
	            String fileToRead = files.get(position).getAbsolutePath();
	            intent.putExtra(EXTRA_FILE, fileToRead);
	            startActivity(intent);
	        }
	        
	        if(item.endsWith(".txt"))
	        {
		        Intent intent = new Intent(ScanActivity.this, DisplayTxt.class);
	            String fileToRead = files.get(position).getAbsolutePath();
	            intent.putExtra(EXTRA_FILE, fileToRead);
	            startActivity(intent);
	        }
	        
	        if(item.endsWith(".epub"))
	        {
		        Intent intent = new Intent(ScanActivity.this, DisplayEpubWeb.class);
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

}
