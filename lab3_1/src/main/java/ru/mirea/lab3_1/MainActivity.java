package ru.mirea.lab3_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2, btn3;
    private ArrayList<Item> list;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private RandomFour rf;
    private SimpleDateFormat formatForDateNow;
    private String[] names;
    private int countData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("lab3_1");

        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        list = new ArrayList<>();
        Intent j = getIntent();
        rf = j.getParcelableExtra("random");
        names = j.getStringArrayExtra("names");
        countData = 5;
    }

    @Override
    public void onClick(View v) {
        dbHelper = new DBHelper(this);
        Intent i;
        switch (v.getId()) {
            case R.id.btn1:
                database = dbHelper.getReadableDatabase();
                Cursor cursor1 = database.query(DBHelper.TABLE_STUDENTS, null, null, null, null, null, null);
                if (cursor1.moveToFirst()) {
                    do {
                        int idColIndex = cursor1.getColumnIndex("_id");
                        int nameColIndex = cursor1.getColumnIndex("FIO");
                        int dateColIndex = cursor1.getColumnIndex("Date");
                        list.add(new Item(cursor1.getInt(idColIndex), cursor1.getString(nameColIndex), cursor1.getString(dateColIndex)));
                    } while (cursor1.moveToNext());
                    i = new Intent(MainActivity.this, DataActivity.class);
                    i.putExtra("listData", list);
                    startActivity(i);
                    list.clear();
                } else
                    cursor1.close();
                break;
            case R.id.btn2:
                database = dbHelper.getReadableDatabase();
                String[] projection = {DBHelper.KEY_ID, DBHelper.KEY_FIO, DBHelper.KEY_DATE};
                Cursor cursor2 = database.query(DBHelper.TABLE_STUDENTS, projection, null, null, null, null, null);
                countData = cursor2.getCount() + 1;
                if (countData - 1 < 29) {
                    ContentValues contentValues1 = new ContentValues();
                    int q = rf.generate();
                    contentValues1.put(DBHelper.KEY_FIO, names[q]);
                    formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd HH:mm:ss:SS");
                    contentValues1.put(DBHelper.KEY_DATE, formatForDateNow.format(new Date()));
                    database.insert(DBHelper.TABLE_STUDENTS, null, contentValues1);
                    contentValues1.clear();
                    if (countData - 1 < 28) {
                        showToastAdd(v, "Запись добавлена");
                    } else {
                        showToastAdd(v, "Все одногруппники добавлены");
                        btn2.setEnabled(false);
                    }
                }
                cursor2.close();
                database.close();
                break;
            case R.id.btn3:
                database = dbHelper.getReadableDatabase();
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("fio","Иванов Иван Иванович");
                database.update(DBHelper.TABLE_STUDENTS, contentValues2, "_id=" + countData, null);
                showToastAdd(v, "Последняя запись изменена");

                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    public void showToastAdd(View view, String text) {
        //создаём и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 200);
        toast.show();
    }

    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("list", list);
        outState.putInt("countData", countData);
        outState.putParcelable("rf", rf);
        if (!btn2.isEnabled()) {
            outState.putBoolean("btn2_en", false);
        } else outState.putBoolean("btn2_en", true);

        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        list = savedInstanceState.getParcelableArrayList("list");
        countData = savedInstanceState.getInt("countData");
        rf = savedInstanceState.getParcelable("rf");
        btn2.setEnabled(savedInstanceState.getBoolean("btn2_en"));
    }

}
