package ru.mirea.lab3_1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreen extends AppCompatActivity {
    private DBHelper dbHelper;
    private RandomFour rf;
    private SimpleDateFormat formatForDateNow;
    private SQLiteDatabase database;
    private String[] names;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splashscreen);
            dbHelper = new DBHelper(SplashScreen.this);
            if(savedInstanceState == null)
                new PrefetchData().execute();

        }

        private class PrefetchData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                names = getResources().getStringArray(R.array.names);
                rf = new RandomFour(names.length);
                database = dbHelper.getWritableDatabase();
                database.delete("STUDENTS", null, null);
                ContentValues contentValues = new ContentValues();
                formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd HH:mm:ss:SS");
                for (int i = 0; i < 5; i++) {
                    contentValues.put(DBHelper.KEY_FIO, names[rf.generate()]);
                    contentValues.put(DBHelper.KEY_DATE, formatForDateNow.format(new Date()));
                    database.insert(DBHelper.TABLE_STUDENTS, null, contentValues);
                    contentValues.clear();
                }
                database.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.putExtra("random", rf);
                i.putExtra("names", names);
                startActivity(i);
                finish();
            }
        }


}
