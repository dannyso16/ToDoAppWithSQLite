package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etCompany;
    private EditText etPrice;
    private DatabaseHelper2 helper;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCompany = findViewById(R.id.et_company);
        etPrice = findViewById(R.id.et_price);
        ListView lvShow = findViewById(R.id.lv_show);

        // set button listener
        Button insertBtn = findViewById(R.id.btn_insert);
        insertBtn.setOnClickListener(new ButtonListener());

        // load db
        helper = new DatabaseHelper2(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        // select
        Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
        Log.d("debug", "db -- # of columns " + cursor.getColumnCount());
        Log.d("debug", "db -- # of raws " + cursor.getCount());

        // adapter用準備
        String[] from = {"company", "stockprice"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_2,
                cursor, from, to, 0);

        lvShow.setAdapter(adapter);
        // listener
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug", "short clicked : id = " + i);

                TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
                String s1 = tv1.getText().toString();
                TextView tv2 = (TextView)view.findViewById(android.R.id.text2);
                String s2 = tv2.getText().toString();
                Toast.makeText(MainActivity.this,
                        "country : " + s1 + "\nstockprice : " + s2,
                        Toast.LENGTH_LONG).show();
            }
        });

        lvShow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug", "Long clicked : _id = " + i);

                TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
                String s1 = tv1.getText().toString();
                TextView tv2 = (TextView)view.findViewById(android.R.id.text2);
                String s2 = tv2.getText().toString();
                deleteData(s1, s2);

                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,
                        "Selected Todo is deleted.",
                        Toast.LENGTH_LONG).show();

                return true;
            }
        });
    }


    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            SQLiteDatabase db = helper.getWritableDatabase();

            String key = etCompany.getText().toString();
            String value = etPrice.getText().toString();

            insertData(db, key, Integer.valueOf(value));
            // 更新

            Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
            adapter.changeCursor(cursor);
            adapter.notifyDataSetChanged();

            etCompany.setText("");
            etPrice.setText("");
            Log.d("debug", "onCllck() is called");
        }
    }

    private void insertData(SQLiteDatabase db, String company, int price) {
        ContentValues values = new ContentValues();
        values.put("company", company);
        values.put("stockprice", price);

        db.insert("testdb", null, values);
    }

    private void deleteData(String company, String stockprice) {
        // DatabaseHelper2 helper = new DatabaseHelper2(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);

        try {

            db.execSQL("delete from testdb " +
                       "where company='" + company + "' AND " +
                       "stockprice=" + stockprice);
            Log.d("debug", "deleteData() is called");
            Log.d("debug", "name=" + company);
        }
        finally {
            db.close();
        }

    }

}
