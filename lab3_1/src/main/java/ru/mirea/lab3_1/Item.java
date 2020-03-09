package ru.mirea.lab3_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int id;
    private String fio;
    private String date;

    public Item(int id, String fio, String date){
        this.id = id;
        this.fio = fio;
        this.date = date;
    }

    public String getID() {
        return Integer.toString(id);
    }

    public String getFIO() {
        return fio;
    }

    public String getDate() {
        return date;
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.fio = in.readString();
        this.date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.fio);
        dest.writeString(this.date);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
