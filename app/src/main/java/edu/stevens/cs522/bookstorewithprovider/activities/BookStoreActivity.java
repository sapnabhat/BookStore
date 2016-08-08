package edu.stevens.cs522.bookstorewithprovider.activities;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import contracts.BookContract;
import databases.BookAdapter;
import edu.stevens.cs522.bookstorewithprovider.R;

public class BookStoreActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = BookStoreActivity.class.getCanonicalName();

    // These are request codes for subactivity request calls
    static final private int ADD_REQUEST = 1;

    private static final int BOOK_LOADER_ID = 1;
    public static final int AUTH_LOADER_ID = 2;
    @SuppressWarnings("unused")
    static final private int CHECKOUT_REQUEST = ADD_REQUEST + 1;

    @SuppressWarnings("unused")
    private ArrayList<Integer> deleteList = new ArrayList<Integer>();
    private static ArrayList<Integer> referenceForDeletion = new ArrayList<Integer>();
    private ListView cartList;
    public final static String EXTRA = "E";
    int count;

    LoaderManager loaderManager = getLoaderManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        cartList = (ListView) findViewById(android.R.id.list);
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        cartList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        cartList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if (cartList.isItemChecked(i)) {
                    count = count + 1;
                    actionMode.setTitle(count + " book(s) selected");
                    deleteList.add(i + 1);
                } else {
                    count--;
                    actionMode.setTitle(count + " book(s) selected");
                    deleteList.remove(i);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_cab, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        callDelete();
                        deleteList.clear();
                        count = 0;
                        actionMode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                count = 0;
                deleteList.clear();
            }
        });
        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookStoreActivity.this, ViewBookActivity.class);
                intent.putExtra("POS", position + 1);
                intent.putExtra("Detail", id);
                startActivity(intent);
            }
        });

    }

    public void callDelete() {
        Collections.sort(deleteList);
        for (int i = deleteList.size() - 1; i >= 0; i--) {
            int j = deleteList.get(i);
            int index = referenceForDeletion.get(j - 1);
            referenceForDeletion.remove(j - 1);
            Uri uri = BookContract.CONTENT_URI(index);
            ContentResolver contentResolverDelete = getContentResolver();
            contentResolverDelete.delete(uri, null, null);
        }
        loaderManager.restartLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // ADD, DELETE and CHECKOUT options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bookstore, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // ADD option for adding a book
        switch (item.getItemId()) {
            case R.id.add:
                Intent addIntent = new Intent(this, AddBookActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST);
                return true;
            case R.id.checkout:
                Intent checkIntent = new Intent(this, CheckoutActivity.class);
                int count = cartList.getCount();
                checkIntent.putExtra(EXTRA, count);
                startActivityForResult(checkIntent, CHECKOUT_REQUEST);
                return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Use ADD_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        if (resultCode == ADD_REQUEST) {
            loaderManager.restartLoader(BOOK_LOADER_ID, null, this);
        } else if (resultCode == CHECKOUT_REQUEST) {
            referenceForDeletion.clear();
            ContentResolver contentResolverCheckout = getContentResolver();
            contentResolverCheckout.delete(BookContract.CONTENT_URI, null, null);
            loaderManager.restartLoader(BOOK_LOADER_ID, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case BOOK_LOADER_ID:
                return new CursorLoader(this, BookContract.CONTENT_URI, null, null, null, null);

            case AUTH_LOADER_ID:
                return new CursorLoader(this, BookContract.CONTENT_URI_AUTH, null, null, null, null);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        BookAdapter bookAdapter = new BookAdapter(this, cursor);
        int i = cursor.getCount();
        cursor.moveToFirst();
        referenceForDeletion.clear();
        for (int j = 0; j < i; j++) {
            int bookSelected = BookContract.getId(cursor);
            referenceForDeletion.add(bookSelected);
            cursor.moveToNext();
        }
        cartList.setAdapter(bookAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        BookAdapter bookAdapter = new BookAdapter(this, null);
        bookAdapter.swapCursor(null);

    }
}
