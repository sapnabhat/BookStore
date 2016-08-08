package edu.stevens.cs522.bookstorewithprovider.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import contracts.BookContract;

/**
 * Created by Sapna on 2/28/2016.
 */
public class Book implements Parcelable {

    public int id;
    public String title;
    public String isbn;
    public String price;


    public Book(Parcel in) {
        this.title = in.readString();
        this.price = in.readString();
    }

    public Book(String title, String isbn, String price) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(this.title);
        out.writeString(this.price);
    }

    public int describeContents() {
        return 0;
    }

    public static Creator<Book> CREATOR = new Creator<Book>() {
        public Book
        createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(Cursor cursor) {
        this.id = BookContract.getId(cursor);
        this.title = BookContract.getTitle(cursor);
        this.isbn = BookContract.getIsbn(cursor);
        this.price = BookContract.getPrice(cursor);
    }

    public void writeToProvider(ContentValues values) {
        BookContract.putTitle(values, this.title);
        BookContract.putIsbn(values, this.isbn);
        BookContract.putPrice(values, this.price);
    }

    public String toString() {
        String x = "";
        x = x + this.title + '\n' + this.price;
        return x;
    }
}
