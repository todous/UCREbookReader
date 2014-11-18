package com.ucr.ebookreader;

import android.app.Activity;
import com.parse.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PaymentActivity extends Activity {

	String bookObjId;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setContentView(R.layout.payment);
	    
	    Bundle extras = getIntent().getExtras();
	    bookObjId = extras.getString("passedId");
	    
	    Button submit = (Button) findViewById(R.id.button1);
	    
	    submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			   //create new purchased book object
			   ParseObject purchase = new ParseObject("PurchasedBooks");
			   purchase.put("user", ParseUser.getCurrentUser().getUsername());
			   purchase.put("bookid", bookObjId);
			   purchase.saveInBackground();
			   Toast.makeText(PaymentActivity.this, "Book successfully purchased", Toast.LENGTH_SHORT).show();
			   //update number of times book was purchased
			   ParseQuery<ParseObject> updatebook = ParseQuery.getQuery("Books");
			   updatebook.getInBackground(bookObjId, new GetCallback<ParseObject>() {
          		 public void done(ParseObject object, ParseException e) {
				         if (e == null) {				     				        	 
				        	 object.increment("timesPurchased");
				        	 object.saveInBackground();
				         }
				         else {
				        	 
				         }
          	 }
              });
			   //go back to pickedbook.xml
			   Intent intent = new Intent(PaymentActivity.this, PickedBook.class);
			   intent.putExtra("passedId", bookObjId);
			   startActivity(intent);
 
			}
		});
	    
	}

}
