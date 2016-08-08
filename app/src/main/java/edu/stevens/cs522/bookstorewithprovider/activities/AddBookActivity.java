package edu.stevens.cs522.bookstorewithprovider.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import contracts.BookContract;
import edu.stevens.cs522.bookstorewithprovider.R;
import edu.stevens.cs522.bookstorewithprovider.entities.Author;
import edu.stevens.cs522.bookstorewithprovider.entities.Book;

public class AddBookActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // ADD and CANCEL options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addbook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add:
                EditText titleInfo = (EditText) findViewById(R.id.add_title);
                EditText authorInfo = (EditText) findViewById(R.id.add_author);
                EditText isbnInfo = (EditText) findViewById(R.id.add_isbn);
                EditText priceInfo = (EditText) findViewById(R.id.add_price);
                String titleStr = titleInfo.getText().toString();
                String authorStr = authorInfo.getText().toString();
                String isbnStr = isbnInfo.getText().toString();
                String priceStr = isbnInfo.getText().toString();
                if (titleStr.isEmpty() || authorStr.isEmpty() || isbnStr.isEmpty() || isbnStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Some text fields are empty ", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    int bookObj = addBook();
                    getAuthor(bookObj);
                    Intent i = new Intent();
                    setResult(1, i);
                    finish();
                    return true;
                }
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                return super.onOptionsItemSelected(item);
        }
        return true;
        // CANCEL: cancel the search request
    }

    ContentValues contentValuesForFirstAuthor = new ContentValues();

    public void getAuthor(int fk) {
        EditText editText = (EditText) findViewById(R.id.add_author);
        String msg = editText.getText().toString();
        if (msg.contains(",")) {
            String[] authorSplit = msg.split(",");
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValuesForNextAuthor = new ContentValues();
            Author authorFirst = new Author(authorSplit[0], fk);
            Author authorSecond = new Author(authorSplit[1], fk);
            authorFirst.writeToProvider(contentValuesForFirstAuthor);
            authorSecond.writeToProvider(contentValuesForNextAuthor);
            contentResolver.insert(BookContract.CONTENT_URI_AUTH, contentValuesForFirstAuthor);
            contentResolver.insert(BookContract.CONTENT_URI_AUTH, contentValuesForNextAuthor);
        } else {
            Author a = new Author(msg, fk);
            ContentResolver cr = getContentResolver();
            a.writeToProvider(contentValuesForFirstAuthor);
            Uri u = cr.insert(BookContract.CONTENT_URI_AUTH, contentValuesForFirstAuthor);
            String idd = u.getPathSegments().get(1);
        }
    }

    public int addBook() {
        EditText titleText = (EditText) findViewById(R.id.add_title);
        String title_s = titleText.getText().toString();
        EditText isbnText = (EditText) findViewById(R.id.add_isbn);
        String isbn_s = isbnText.getText().toString();
        EditText priceText = (EditText) findViewById(R.id.add_price);
        String price_s = priceText.getText().toString();
        ContentValues contentValuesForBook = new ContentValues();
        Book book = new Book(title_s, isbn_s, price_s);
        book.writeToProvider(contentValuesForBook);
        ContentResolver contentResolverForBook = getContentResolver();
        Uri bookRow = contentResolverForBook.insert(BookContract.CONTENT_URI, contentValuesForBook);
        int rowId = Integer.parseInt(bookRow.getLastPathSegment());
        return rowId;
    }

}
