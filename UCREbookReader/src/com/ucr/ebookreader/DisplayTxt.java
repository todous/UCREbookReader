package com.ucr.ebookreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class DisplayTxt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String fileName = intent.getStringExtra(Welcome.EXTRA_FILE);		
		
		//setup WebView
		WebView wv = new WebView(this);
		wv.getSettings().setJavaScriptEnabled(true);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) 
		{
			wv.getSettings().setAllowFileAccessFromFileURLs(true);
			wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
		} 
		
        StringBuilder sb = new StringBuilder();
        sb.append(readText(fileName));
        wv.loadData(sb.toString(), "text/plain", "UTF-8");
        setContentView(wv);
		
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	
	public String readText(String file)
	{
		int istr = 0;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while((istr = br.read()) != -1 )
			{
				 sb.append((char)istr);
			}
			br.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	
	}

}
