package ru.mirea.lab3_1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

public class RandomFour implements Parcelable
{
    private ArrayList<Integer> input;
    private final Random rnd;
    private final int Count;
    private int genCount;
    public RandomFour(int in)
    {
        Count = in;
        rnd = new Random();
        input = new ArrayList<>();
        genCount = 0;
    }

    public int generate()
    {
        if (genCount >= Count) {
            return -1;
        }
        int next;
        boolean T = false;
        do {
            next = rnd.nextInt(Count);
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i) == next) {
                    T = true;
                    break;
                } else T = false;
            }
        } while(T);
        input.add(next);
        genCount++;
        return next;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Count);
        dest.writeInt(this.genCount);
        dest.writeList(this.input);
        dest.writeValue(this.rnd);

    }

    protected RandomFour(Parcel in) {
        this.Count = in.readInt();
        this.genCount = in.readInt();
        this.input = (ArrayList<Integer>) in.readArrayList(null);
        this.rnd = (Random)in.readValue(getClass().getClassLoader());
    }


    public static final Parcelable.Creator<RandomFour> CREATOR = new Creator<RandomFour>() {
        @Override
        public RandomFour createFromParcel(Parcel source) {
            return new RandomFour(source);
        }

        @Override
        public RandomFour[] newArray(int size) {
            return new RandomFour[size];
        }
    };

}