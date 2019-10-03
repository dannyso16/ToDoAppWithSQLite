package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

    private EditText etName;
    private EditText etDetail;
    private DatabaseHelper2 helper;
    private ToDoAdapter adapter;
    private ArrayList<ToDoItem> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view の取得
        etName = findViewById(R.id.et_name);
        etDetail = findViewById(R.id.et_detail);
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

                String name = etName.getText().toString();
                String detail = etDetail.getText().toString();

                // 空欄がある場合，再入力を促す
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "EditText is EMPTY !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 更新
                ToDoItem item = new ToDoItem();
                item.setName(name);
                item.setDetail(detail);
                insertData(item);

                // 空欄に戻す
                etName.setText("");
                etDetail.setText("");
                Log.d("listener", "INSERT btn is onCllck()");
            }
        });

        // load db
        helper = new DatabaseHelper2(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);

            toDoList = new ArrayList<>();
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                ToDoItem item = new ToDoItem();
                int idxName = cursor.getColumnIndex("name");
                item.setName(cursor.getString(idxName));
                int idxDetail = cursor.getColumnIndex("detail");
                item.setDetail(cursor.getString(idxDetail));
                toDoList.add(item);
            }
        } finally {
            db.close();
        }



        // adapter作成してListviewに適用
        adapter = new ToDoAdapter(MainActivity.this);
        adapter.setToDoList(toDoList);
        lvShow.setAdapter(adapter);

        /*
         * ListView のリスナ
            * 左の□：押すとそのtodoを削除
            * 右の□：押すと「重要」になる
            * 文字　：押すとポップアップで内容がでる
         */
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long l) {
                // Log.d("listener", "view.getId(): " + view.getId());
                ListView listView = (ListView)parent;
                final ToDoItem item = (ToDoItem) listView.getItemAtPosition(i);

                switch (view.getId()) {
                    case R.id.cb_done_in_lv:
                        deleteData(item, i);
                        break;
                    case R.id.cb_star_in_lv:
                        // TODO: 2019/10/03 starを付けた時の処理をかく
                        break;

                    default:
                        // ボタン以外の領域をタップしたとき(viewはなく-1が入るはず)
                        // タップしたアイテムの取得
                        Log.d("debug", "Tap: " + i);

//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("Tap No. " + i);
//                        builder.setMessage(item.getName());
//                        builder.show();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(item.getName())
                                .setMessage(item.getDetail())
                                .setPositiveButton("できたー", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("debug", "完了 is clicked");
                                        deleteData(item, i);

                                    }
                                })
                                .setNegativeButton("まだー", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d("debug", "まだー is clicked");
                                    }
                                })
                                .show();
                        break;

                }

            }
        });
    }
    


    private void insertData(ToDoItem item) {
        String name = item.getName();
        String detail = item.getDetail();
        toDoList.add(item);

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO testdb (name, detail) VALUES (?, ?);",
                new String[]{name, detail});
            adapter.notifyDataSetChanged();
            Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
            Log.d("debug", "insertData() is called");
            Log.d("debug", "rows : " + cursor.getCount());
        }
        finally {
            db.close();
        }
    }

    private void deleteData(ToDoItem item, int index) {
        String name = item.getName();
        String detail = item.getDetail();
        toDoList.remove(index);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM testdb WHERE name=? AND detail=?;",
                    new String[]{name, detail});
            adapter.notifyDataSetChanged();

            Cursor cursor = db.rawQuery("SELECT * FROM testdb", null);
            Log.d("debug", "delteeData() is called");
            Log.d("debug", "rows : " + cursor.getCount());

        }
        finally {
            db.close();
        }
    }
}
