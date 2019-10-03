package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etCompany;
    private EditText etPrice;
    private DatabaseHelper2 helper;
    private SimpleCursorAdapter adapter;
    private ArrayList<ToDoItem> toDoList;
    // TODO: 2019/10/03 adapterをカスタムのものに置き換える 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view の取得
        etCompany = findViewById(R.id.et_company);
        etPrice = findViewById(R.id.et_price);
        ListView lvShow = findViewById(R.id.lv_show);

        // set button listener
        Button testBtn = findViewById(R.id.btn_test);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        Button insertBtn = findViewById(R.id.btn_insert);
        insertBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = helper.getWritableDatabase();

                String key = etCompany.getText().toString();
                String value = etPrice.getText().toString();

                if (key.isEmpty() | value.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "EditText is EMPTY !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                insertData(db, key, value);

                // 更新
                Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();

                // 空欄に戻す
                etCompany.setText("");
                etPrice.setText("");
                Log.d("debug", "INSERT btn is onCllck()");
            }
        });

        // load db
        helper = new DatabaseHelper2(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);

        Log.d("debug", "db init # of columns " + cursor.getColumnCount());
        Log.d("debug", "db init # of rows " + cursor.getCount());


        // adapter作成してListviewに適用
        String[] from = {"company", "stockprice"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_2,
                cursor, from, to, 0);

        lvShow.setAdapter(adapter);


        /*
        ListView のリスナ
            * short tap でインフォ
            * long tap で削除
         */
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug", "short clicked : id = " + i);

                TextView tv1 = view.findViewById(android.R.id.text1);
                String s1 = tv1.getText().toString();
                TextView tv2 = view.findViewById(android.R.id.text2);
                String s2 = tv2.getText().toString();
                Toast.makeText(MainActivity.this,
                        "country : " + s1 + "\nstockprice : " + s2,
                        Toast.LENGTH_SHORT).show();
            }
        });

        lvShow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug", "Long clicked : _id = " + i);

                TextView tv1 = view.findViewById(android.R.id.text1);
                String s1 = tv1.getText().toString();
                TextView tv2 = view.findViewById(android.R.id.text2);
                String s2 = tv2.getText().toString();
                deleteData(s1, s2);

                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,
                        "Selected Todo is deleted.",
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }
    

    private void insertData(SQLiteDatabase db, String company, String price) {
        db.execSQL("INSERT INTO testdb (company, stockprice) VALUES (?, ?);",
                new String[]{company, price});
    }

    private void deleteData(String company, String stockprice) {
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM testdb WHERE company=? AND stockprice=?;",
                    new String[]{company, stockprice});
            Log.d("debug", "deleteData() is called");
            Log.d("debug", "name=" + company);
        }
        finally {
            db.close();
        }

    }

}
