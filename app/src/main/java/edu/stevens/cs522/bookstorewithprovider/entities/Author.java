package edu.stevens.cs522.bookstorewithprovider.entities;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import contracts.BookContract;

/**
 * Created by Sapna on 2/28/2016.
 */
public class Author implements Parcelable {

    public String Name;
    public int bookfk;

    public Author(String Name,int fk) {
        this.Name = Name;
        this.bookfk=fk;
    }
    public String toString() {
        String x="";
        x=x+Name;
        return x;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] { this.Name });
    }

    public Author(Parcel in) {
        String[] data = new String[1];
        in.readStringArray(data);
        this.Name = data[0];
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public void writeToProvider(ContentValues values){
        BookContract.putName(values, this.Name);
        BookContract.putbookfk(values,this.bookfk);
    }

}
