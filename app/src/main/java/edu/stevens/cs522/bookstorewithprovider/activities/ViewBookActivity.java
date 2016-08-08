package edu.stevens.cs522.bookstorewithprovider.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import contracts.BookContract;
import edu.stevens.cs522.bookstorewithprovider.R;

public class ViewBookActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView bookView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // ADD and CANCEL options
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.back:
                finish();
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch(i)
        {
            case 0:
                Intent intent = getIntent();
                long id = intent.getLongExtra("Detail",0);
                Uri ui=BookContract.CONTENT_URI(id);
                return new CursorLoader(this,ui,null,null,null,null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        String bookDisplay = "";
        bookDisplay = bookDisplay + "Title : " + BookContract.getTitle(cursor) + '\n' + '\n' +
                "Author : " + BookContract.getAuthor(cursor) + '\n' + '\n' +
                "ISBN : " + BookContract.getIsbn(cursor) + '\n' + '\n' +
                "Price : $" + BookContract.getPrice(cursor);
        bookView = (TextView) findViewById(R.id.bookView);
        bookView.setText(bookDisplay);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
