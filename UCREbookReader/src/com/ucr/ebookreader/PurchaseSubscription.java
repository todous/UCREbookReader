package com.ucr.ebookreader;

import android.app.Activity;
import android.content.Intent;

import com.parse.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PurchaseSubscription extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.payment);
	    
	    Button submit = (Button) findViewById(R.id.button1);
	    submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseUser.getCurrentUser().put("monthlySubscription", true);
				ParseUser.getCurrentUser().saveInBackground();
				Toast.makeText(PurchaseSubscription.this, "Subscription purchased", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PurchaseSubscription.this, Welcome.class);
				startActivity(intent);
			}
		});
	    
	}

}
