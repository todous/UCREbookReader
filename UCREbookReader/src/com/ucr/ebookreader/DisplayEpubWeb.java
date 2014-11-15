package com.ucr.ebookreader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dteviot.epubviewer.Bookmark;
import com.dteviot.epubviewer.BookmarkDialog;
import com.dteviot.epubviewer.EpubWebView;
import com.dteviot.epubviewer.EpubWebView30;
import com.dteviot.epubviewer.IAction;
import com.dteviot.epubviewer.IResourceSource;
import com.dteviot.epubviewer.ResourceResponse;
import com.dteviot.epubviewer.epub.Book;
import com.dteviot.epubviewer.epub.TableOfContents;
import com.parse.ParseUser;


public class DisplayEpubWeb extends Activity implements IResourceSource{
	
    private final static int LIST_CHAPTER_ACTIVITY_ID = 1; 
    private final static int CHECK_TTS_ACTIVITY_ID = 2; 
 
	private EpubWebView EpubView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

		ParseUser currentUser = ParseUser.getCurrentUser();
		String fileName;
		
		//if user logged in get intent from Welcome and display epub file
		if (currentUser != null) {
			Intent intent = getIntent();
			fileName = intent.getStringExtra(Welcome.EXTRA_FILE);
		}		
		//if user not logged in get intent from
		else {
			Intent intent = getIntent();
			fileName = intent.getStringExtra(WelcomeAnon.EXTRA_FILE);
		}
		
	    EpubView = new EpubWebView30(this); 
	    setContentView(EpubView);
	    EpubView.setBook(fileName);
	    EpubView.loadChapter(null);
	
	    if (savedInstanceState != null) {
	        EpubView.gotoBookmark(new Bookmark(savedInstanceState));
	    } 
	    else {
            Bookmark bookmark = new Bookmark(this);
            EpubView.gotoBookmark(bookmark);
        }

	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_epub_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // item select 
        switch (item.getItemId()) {
        case R.id.menu_bookmark:
            launchBookmarkDialog();
            return true;
        case R.id.menu_chapters:
            launchChaptersList();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECK_TTS_ACTIVITY_ID) {
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case LIST_CHAPTER_ACTIVITY_ID:
        	        Uri chapterUri = data.getParcelableExtra(ListChaptersActivity.CHAPTER_EXTRA);
        	        EpubView.loadChapter(chapterUri);
                    break;
            }
        }
    }
	
 

	//function
    private void launchChaptersList() {
        Book book = EpubView.getBook(); 
        if (book == null) {
            
        } 
        else {
            TableOfContents toc = book.getTableOfContents();
            if (toc.size() == 0) { 
            	
            } 
            else {
            	Intent listChaptersIntent = new Intent(this, ListChaptersActivity.class);
                toc.pack(listChaptersIntent, ListChaptersActivity.CHAPTERS_EXTRA);
                startActivityForResult(listChaptersIntent, 1);
            }
        }
    }


	    private void launchBookmarkDialog() {
	        BookmarkDialog dlg = new BookmarkDialog(this);
	        dlg.show();
	        dlg.setSetBookmarkAction(saveBookmark);
	        dlg.setGotoBookmarkAction(goToBookmark);
	    }

	    protected void onSaveInstanceState (Bundle outState) {
	        super.onSaveInstanceState(outState);
	        Bookmark bookmark = EpubView.getBookmark();
	        if (bookmark != null) {
	            bookmark.save(outState);
	        }
	    }

	    private IAction saveBookmark = new IAction() {
	        public void doAction() {
	            Bookmark bookmark = EpubView.getBookmark();
	            if (bookmark != null) {
	                bookmark.saveToSharedPreferences(DisplayEpubWeb.this);
	            }
	        }
	    };

	    private IAction goToBookmark = new IAction() {
	        public void doAction() {
	            EpubView.gotoBookmark(new Bookmark(DisplayEpubWeb.this));
	        }
	    };

		@Override
	    public ResourceResponse fetch(Uri resourceUri) {
	        return EpubView.getBook().fetch(resourceUri);
	    }
}