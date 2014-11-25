package com.ucr.ebookreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class DisplayTxt extends Activity {
	
	ArrayList<TextView> TextViews;
	ArrayList<String> Book;
	private ViewFlipper viewFlipper;
	TextView tv ;
	int chunk = 0;
	int end = 30;
	
    private final static int CHANGE_SIZE = 1; 
    private final static int CHANGE_FONT = 2; 
    private final static int CHANGE_BACKGROUND = 3; 
    
    private final static int BLACK = 0xff000000;
    public final static int DARKGRAY = 0xff444444;
    public final static int GREY = 0xff888888;
    public final static int WHITE = 0xffffffff;
    public final static int MAROON = 0xff800000;
    public final static int NAVY = 0xff000080;
    public final static int OLIVE = 0xff808000;
    
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_txt);
        
		Intent intent = getIntent();
		String fileName = intent.getStringExtra(Welcome.EXTRA_FILE);	
		
		Book = readText(fileName);
		tv = (TextView) findViewById(R.id.textV);
		tv.setHorizontallyScrolling(true);
		tv.setMaxLines(30);
		tv.setScrollContainer(true);
		tv.setMovementMethod(new ScrollingMovementMethod());
		tv.setText("");
		for(int i = chunk ; i < end + chunk; i++)
		{
			tv.append(Book.get(i));
		}
		tv.refreshDrawableState();
		//chunk += 30;
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_txt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // item select 
        switch (item.getItemId()) {
        case R.id.action_previous:
        	tv.setText("");
        	chunk -= 30;
        	
        	if(chunk < 0)
        		chunk = 0;
        	
        	for(int i = chunk ; i < end + chunk; i++)
    		{
    			tv.append(Book.get(i));
    		}
        	tv.refreshDrawableState();
            return true;
        case R.id.action_next:
        	tv.setText("");
        	
        	chunk += 30;
        	
        	if(chunk < 0)
        		chunk = 0;
        	
        	if(end + chunk > Book.size() )
        	{
        		if(chunk > Book.size())
        			break;
        		for(int i = chunk ; i < Book.size(); i++)
        		{
        			tv.append(Book.get(i));
        		}
        	}
        	else
        	{
	        	for(int i = chunk ; i < end + chunk; i++)
	    		{
	    			tv.append(Book.get(i));
	    		}
        	}
        	tv.refreshDrawableState();
            return true;
        case R.id.action_change_txt_size:
        	launchChangeSize();
            return true;
        case R.id.action_change_txt_font:
        	launchChangeFont();
            return true;
        case R.id.action_change_background_color:
        	launchChangeBackground();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
		return true;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
     }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);         
    }
	
	public ArrayList<String> readText(String file)
	{
		String istr = null;
		ArrayList<String> str = new ArrayList<String>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));

			while(((istr = br.readLine())) != null )
			{
				str.add(istr+"\n");
			}
			
			br.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String item;
        
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

            case CHANGE_SIZE:
            	item = data.getStringExtra(ListTextSize.TEXT_SIZE);
 		        int size = Integer.parseInt(item);
 	            tv.setTextSize(size);
                break;
            case CHANGE_FONT:
            	item = data.getStringExtra(ListTextFont.TEXT_FONT);
 		        String font = item;
 	            Typeface f = Typeface.createFromAsset((DisplayTxt.this).getAssets(), "fonts/"+font);
 	            tv.setTypeface(f);
                break;
            case CHANGE_BACKGROUND:
            	item = data.getStringExtra(ListTextBackground.BACKGROUND_COLOR);
            	if(item.equals("black"))
            	{
 		        	tv.setTextColor(WHITE);
 		        	tv.setBackgroundColor(BLACK);
            	}
            	else if(item.equals("darkgray"))
            	{
	        		tv.setTextColor(WHITE);
	        		tv.setBackgroundColor(DARKGRAY);

            	}
            	else if(item.equals("grey"))
	            {
	        		tv.setTextColor(BLACK);
	        		tv.setBackgroundColor(GREY);
	            }
            	else if(item.equals("white"))
	            {
	        		tv.setTextColor(BLACK);
	        		tv.setBackgroundColor(WHITE);
	            }
            	else if(item.equals("maroon"))
	            {
	        		tv.setTextColor(BLACK);
	        		tv.setBackgroundColor(MAROON);
	            }
            	else if(item.equals("navy"))
	            {
	        		tv.setTextColor(WHITE);
	        		tv.setBackgroundColor(NAVY);
	            }
            	else if(item.equals("olive"))
	            {
	        		tv.setTextColor(BLACK);
	        		tv.setBackgroundColor(OLIVE);
	            } 
                break;
            }
        }
    }
    
    void launchChangeSize()
    {
    	Intent changeSize = new Intent(this, ListTextSize.class);
        startActivityForResult(changeSize, CHANGE_SIZE);
    }
    
    void launchChangeFont()
    {
    	Intent changeFont = new Intent(this, ListTextFont.class);
        startActivityForResult(changeFont, CHANGE_FONT);
    }
    
    void launchChangeBackground()
    {
    	Intent changeBackground = new Intent(this, ListTextBackground.class);
        startActivityForResult(changeBackground, CHANGE_BACKGROUND);
    }
	


}
