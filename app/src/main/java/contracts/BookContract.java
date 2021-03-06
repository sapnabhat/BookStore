package contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Sapna on 2/28/2016.
 */
public class BookContract {
    public static final String TITLE = "Title";
    public static final String B_ID = "_id";
    public static final String ISBN = "ISBN";
    public static final String PRICE = "Price";

    public static final String A_ID = "_id";
    public static final String NAME = "Name";
    public static final String bookFk = "BookId";
    public static final String Authors = "Authors";

    public static final String Authority = "edu.stevens.cs522.bookstorewithprovider";
    public static final String Path = "books";
    public static final String PathAuth = "authors";

    public static final Uri CONTENT_URI = Uri.parse("content://" + BookContract.Authority + "/" + BookContract.Path);
    public static final Uri CONTENT_URI_AUTH = Uri.parse("content://" + BookContract.Authority + "/" + BookContract.PathAuth);

    public static Uri CONTENT_URI(long id) {
        return Uri.parse("content://" + BookContract.Authority + "/" + BookContract.Path + "/" + id);
    }

    public static Uri CONTENT_URI_AUTH(long id) {
        return Uri.parse("content://" + BookContract.Authority + "/" + BookContract.PathAuth + "/" + id);
    }

    public static long getUriId(Uri uri) {
        return Long.parseLong(uri.getLastPathSegment());
    }


    public static final String contentType = "vnd.android.cursor.dir/vnd." + Authority + "." + Path;
    public static final String contentItemType = "vnd.android.cursor/vnd." + Authority + "." + Path;


    public static String getTitle(Cursor cursor) {
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
    }

    public static int getId(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(B_ID));
    }

    public static String getIsbn(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(ISBN));
    }

    public static String getPrice(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(PRICE));
    }

    public static String getAuthor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(Authors));
    }

    public static String getName(Cursor cursor) {
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndexOrThrow(NAME));
    }

    public static int getFk(Cursor cursor) {
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndexOrThrow(bookFk));
    }


    public static void putTitle(ContentValues values, String title) {
        values.put(TITLE, title);
    }

    public static void putIsbn(ContentValues values, String Isbn) {
        values.put(ISBN, Isbn);
    }

    public static void putPrice(ContentValues values, String Price) {
        values.put(PRICE, Price);
    }

    public static void putAuthor(ContentValues values, String Name) {
        values.put(NAME, Name);
    }

    public static void putbookfk(ContentValues values, int bookfk) {
        values.put(bookFk, bookfk);
    }

    public static void putName(ContentValues values, String name) {
        values.put(NAME, name);
    }

}
