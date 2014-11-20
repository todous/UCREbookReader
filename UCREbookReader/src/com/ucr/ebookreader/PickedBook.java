package com.ucr.ebookreader;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PickedBook extends Activity {
	
	public final static String EXTRA_FILE = "com.ucr.ebookreader.MESSAGE";
	
	String bookId;
	String title;
	String author;
	String genre;
	String currUser = ParseUser.getCurrentUser().getUsername();
	String currReview = "";
	float masterRating;
	float currRating = -1;
	int price;
	TextView textField;
	RatingBar rb;
	ListView lv;
	Button submitReview, buy, open, sample;
	EditText rev;
	Boolean loggedIn = false;
	
	
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pickedbook);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			bookId = extras.getString("passedId");
		}
		
		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
			loggedIn = false;
		} else {
			loggedIn = true;
		}
		
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
		query.getInBackground(bookId, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject book, ParseException e) {
				if (e == null) {
					
					//update number of times book was viewed
					book.increment("timesViewed");
					book.saveInBackground();
					//Extract book info
					title = book.getString("title");
					author = book.getString("author");
					genre = book.getString("genre");
					price = book.getInt("price");
					masterRating = (float) book.getDouble("Rating");
					
					//Set title
					textField = (TextView) findViewById(R.id.textView1);
					textField.setText("Title: " + title);
					
					//Set author
					textField = (TextView) findViewById(R.id.textView2);
					textField.setText("Author: " + author);
					
					//Set genre
					textField = (TextView) findViewById(R.id.textView3);
					textField.setText("Genre: " + genre);
					
					//Set Price
					textField = (TextView) findViewById(R.id.textView4);
					textField.setText("Price: " + price);
					
					//Set Master Rating
					textField = (TextView) findViewById(R.id.textView5);
					textField.setText("Rating: " + masterRating);
					
					//Initialize Ratings Bar
					rb = (RatingBar) findViewById(R.id.ratingBar1);
					
					//Set Ratings Bar Listener
					rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
						@Override
						public void onRatingChanged(RatingBar ratingBar, final float barRating,
								boolean fromUser) {
							editRatingData(barRating);
						}
					});
					
					//Initialize Submit button for Review section
					submitReview = (Button) findViewById(R.id.button2);
					
					//Set Submit Button Listener
					submitReview.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							//check if user has purchased subscription
							if(ParseUser.getCurrentUser().getBoolean("monthlySubscription")) {
								sendReview();
							   }
							else {
							//check if user has purchased book
							ParseQuery<ParseObject> checkPurchase = ParseQuery.getQuery("PurchasedBooks");
							   checkPurchase.whereEqualTo("bookid", bookId);
							   checkPurchase.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
							   checkPurchase.findInBackground(new FindCallback<ParseObject>() {
								     public void done(List<ParseObject> objects, ParseException e) {
								         if (e == null) {
								             if (objects.isEmpty()) {
								            	 Toast.makeText(PickedBook.this, "Book not purchased!", Toast.LENGTH_SHORT).show();								            	   
								             }
								             else {
								            	 sendReview();
								             }
								         } else {
								             
								         }
								     }
							   });
						      }
						}
					});
					
					buy = (Button) findViewById(R.id.button1);
					
					buy.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
						   //check if user has purchased monthly subscription
						   if(ParseUser.getCurrentUser().getBoolean("monthlySubscription")) {
							   Toast.makeText(PickedBook.this, "Subscription already purchased!", Toast.LENGTH_SHORT).show();
						   }
						   else {
						   //check if user has purchased book already
						   ParseQuery<ParseObject> checkPurchase = ParseQuery.getQuery("PurchasedBooks");
						   checkPurchase.whereEqualTo("bookid", bookId);
						   checkPurchase.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
						   checkPurchase.findInBackground(new FindCallback<ParseObject>() {
							     public void done(List<ParseObject> objects, ParseException e) {
							         if (e == null) {
							             if (objects.isEmpty()) {
							            	   //go to payment screen
							            	   Intent intent = new Intent(PickedBook.this, PaymentActivity.class);
											   intent.putExtra("passedId", bookId);
											   startActivity(intent);
											   //finish();
							             }
							             else {
							            	  // user has already purchased book
							            	  Toast.makeText(PickedBook.this, "Book already purchased!", Toast.LENGTH_SHORT).show();
							             }
							         } else {
							             
							         }
							     }
						   });
						   
			 
						  }
						}
					});
					
					open = (Button) findViewById(R.id.button3);
						
					open.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							//check if user has purchased monthly subscription
							if(ParseUser.getCurrentUser().getBoolean("monthlySubscription")) {
								   ParseQuery<ParseObject> book = ParseQuery.getQuery("Books");
					            	 book.getInBackground(bookId, new GetCallback<ParseObject>() {
					            		 public void done(ParseObject object, ParseException e) {
									         if (e == null) {
									        	 //get pdf of book
									        	 String bookUrl = object.getParseFile("text").getUrl();
									        	 //display book
									        	 Intent intent = new Intent(PickedBook.this, DisplayPdf.class);
										         intent.putExtra(EXTRA_FILE, bookUrl);
										         startActivity(intent);
									         }
									         else {
									        	 
									         }
					            	 }
					                });
							   }
							else {
						   //check if user has purchased book
						   ParseQuery<ParseObject> checkPurchase = ParseQuery.getQuery("PurchasedBooks");
						   checkPurchase.whereEqualTo("bookid", bookId);
						   checkPurchase.whereEqualTo("user", ParseUser.getCurrentUser().getUsername());
						   checkPurchase.findInBackground(new FindCallback<ParseObject>() {
							     public void done(List<ParseObject> objects, ParseException e) {
							         if (e == null) {
							             if (objects.isEmpty()) {
							            	 //user has not purchased book yet
							            	 Toast.makeText(PickedBook.this, "Book not purchased!", Toast.LENGTH_SHORT).show();
							            	   
							             }
							             else {		
							            	 //get pdf url of book
							            	 ParseQuery<ParseObject> book = ParseQuery.getQuery("Books");
							            	 book.getInBackground(bookId, new GetCallback<ParseObject>() {
							            		 public void done(ParseObject object, ParseException e) {
											         if (e == null) {
											        	 String bookUrl = object.getParseFile("text").getUrl();	
											        	 //display book
											        	 Intent intent = new Intent(PickedBook.this, DisplayPdf.class);
												         intent.putExtra(EXTRA_FILE, bookUrl);
												         startActivity(intent);
											         }
											         else {
											        	 
											         }
							            	 }
							                });
							             }
							         } 
							             else {
							        
							             
							         }
							     }
						   });
						   
			 
						}
						}
					});
					
					sample = (Button) findViewById(R.id.button4);
					
					sample.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
						   			         //get pdf url of book sample   
							            	 ParseQuery<ParseObject> book = ParseQuery.getQuery("Books");
							            	 book.getInBackground(bookId, new GetCallback<ParseObject>() {
							            		 public void done(ParseObject object, ParseException e) {
											         if (e == null) {
											        	 String sampleUrl = object.getParseFile("sample").getUrl();
											        	 Intent intent = new Intent(PickedBook.this, DisplayPdf.class);
												         intent.putExtra(EXTRA_FILE, sampleUrl);
												         startActivity(intent);
											         }
											         else {
											        	 
											         }
							            	 }
							                });
							             }
							         
							     });
						  
						   
			 
						
			
					
					
					//Initialize Review section
					rev = (EditText) findViewById(R.id.editText1);
					lv = (ListView) findViewById(R.id.listView1);
					
					//Populate Review section
					populateReviews();
				} else {
					// something went wrong
				}
			}
		});
	}
	
	

	public void editRatingData(final float rating) {
		if(loggedIn) {
			ParseQuery<ParseObject> ratingQuery = ParseQuery.getQuery("UserRandR");
			ratingQuery.whereEqualTo("bookid", bookId);
			ratingQuery.whereEqualTo("username", currUser);
			ratingQuery.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(objects.size() > 0) {
						ParseObject userRating = objects.get(0);
						currRating = (float) userRating.getDouble("rating");
						if(currRating == 0.0) {
							userRating = new ParseObject("UserRandR");
						}
						userRating.put("username", currUser);
						userRating.put("bookid", bookId);
						userRating.put("rating", rating);
						userRating.saveInBackground();
					} else {
						ParseObject userRating = new ParseObject("UserRandR");
						
						userRating.put("username", currUser);
						userRating.put("bookid", bookId);
						userRating.put("review", rev.getText().toString());
						userRating.saveInBackground();
					}
				}
			});
		} else {
			Toast.makeText(getApplicationContext(), "You need to be logged in to do that",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(PickedBook.this, LoginSignupActivity.class);
			intent.putExtra("PickedBook", bookId);
			startActivity(intent);
			finish();
		}
	}
	
	public void sendReview() {
		if(loggedIn) {
			ParseQuery<ParseObject> reviewQuery = ParseQuery.getQuery("UserRandR");
			reviewQuery.whereEqualTo("bookid", bookId);
			reviewQuery.whereEqualTo("username", currUser);
			reviewQuery.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(objects.size() > 0)
					{
						ParseObject userReview = objects.get(0);
						
						currReview = userReview.getString("review");
						if(currReview == null) {
							userReview = new ParseObject("UserRandR");
						}
						userReview.put("username", currUser);
						userReview.put("bookid", bookId);
						userReview.put("review", rev.getText().toString());
						userReview.saveInBackground();
						Toast.makeText(getApplicationContext(), "Review sent", Toast.LENGTH_LONG).show();
						rev.setText("");
						populateReviews();
					} else {
						ParseObject userReview = new ParseObject("UserRandR");
						
						userReview.put("username", currUser);
						userReview.put("bookid", bookId);
						userReview.put("review", rev.getText().toString());
						userReview.saveInBackground();
						Toast.makeText(getApplicationContext(), "Review sent", Toast.LENGTH_LONG).show();
						rev.setText("");
						populateReviews();
					}
				}
			});
		} else {
			Toast.makeText(getApplicationContext(), "You need to be logged in to do that",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(PickedBook.this, LoginSignupActivity.class);
			intent.putExtra("PickedBook", bookId);
			startActivity(intent);
			finish();
		}
	}
	
	public void populateReviews() {
		
		ParseQuery<ParseObject> reviewQuery = ParseQuery.getQuery("UserRandR");
		reviewQuery.orderByDescending("updatedAt");
		reviewQuery.whereEqualTo("bookid", bookId);
		reviewQuery.whereExists("review");
		reviewQuery.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(final List<ParseObject> objects, ParseException e) {
				//Variables
				ParseObject obj = null;
				float usrRating = -1;
				String revUser = "";
				String revRev = "";
				String[] revList = new String[objects.size()];
				
				//Create Array for formatting reviews
				for(int i = 0; i < objects.size(); i++) {
					//clear values
					revUser = "";
					revRev = "";
					usrRating = -1;
					
					//set values if they are available
					obj = objects.get(i);
					revUser = obj.getString("username");
					revRev = obj.getString("review");
					usrRating = (float) obj.getDouble("rating");
					
					if(revRev != null && !(revRev.equals(""))) {
						revList[i] = revUser + ": " + revRev;
						if(usrRating != 0.0) {
							revList[i] = revList[i] + "\nMy Rating: " + 
									usrRating + "/5";
						} else {
							revList[i] = revList[i] + "\nMy Rating: User has not rated yet";
						}
					}
				}
				
				//Build ListView from array
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(PickedBook.this,
						android.R.layout.simple_list_item_1, revList);
				
				lv.setAdapter(adapter);
			}
		});
	}
}
