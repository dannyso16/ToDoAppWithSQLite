package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvShow;
    private EditText etCompany;
    private EditText etPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCompany = findViewById(R.id.et_company);
        etPrice = findViewById(R.id.et_price);
        tvShow = findViewById(R.id.tv_show);

        Button insertBtn = findViewById(R.id.btn_insert);
        insertBtn.setOnClickListener(new ButtonListener());

        Button readBtn = findViewById(R.id.btn_read);
        readBtn.setOnClickListener(new ButtonListener());
    }


    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id) {
                case R.id.btn_insert:
                    DatabaseHelper2 helper = new DatabaseHelper2(getApplicationContext());
                    SQLiteDatabase db = helper.getWritableDatabase();

                    String key = etCompany.getText().toString();
                    String value = etPrice.getText().toString();

                    insertData(db, key, Integer.valueOf(value));

                case R.id.btn_read:
                    readData();
            }
            Log.d("debug", "onCllck() is called");
        }
    }

    private void insertData(SQLiteDatabase db, String company, int price) {
        ContentValues values = new ContentValues();
        values.put("company", company);
        values.put("stockprice", price);

        db.insert("testdb", null, values);
    }

    private void readData() {
        Log.d("debug", "********Cursor");

        DatabaseHelper2 helper = new DatabaseHelper2(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            Cursor cursor = db.query(
                    "testdb",
                    new String[] {"company", "stockprice"},
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            StringBuilder sb = new StringBuilder();
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int idxCompany = cursor.getColumnIndex("company");
                int idxStock = cursor.getColumnIndex("stockprice");
                sb.append(cursor.getString(idxCompany));
                sb.append(": ");
                sb.append(cursor.getInt(idxStock));
                sb.append("\n");
            }

            Log.d("debug", "********" + sb.toString());
            tvShow.setText(sb.toString());
        }
        finally {
            db.close();
        }
    }
}
