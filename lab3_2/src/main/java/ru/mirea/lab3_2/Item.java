package ru.mirea.lab3_2;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int id;
    private String surname;
    private String name;
    private String middle_name;
    private String date;

    public Item(int id, String surname, String name,String middle_name, String date){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middle_name = middle_name;
        this.date = date;
    }

    public String getID() {
        return Integer.toString(id);
    }

    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public String getMiddle_name() {
        return middle_name;
    }


    public String getDate() {
        return date;
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.surname = in.readString();
        this.name = in.readString();
        this.middle_name = in.readString();
        this.date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.surname);
        dest.writeString(this.name);
        dest.writeString(this.middle_name);
        dest.writeString(this.date);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
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
