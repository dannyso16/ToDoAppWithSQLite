package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    ListView lvTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ArrayList<ToDoItem> list = new ArrayList<>();
        ToDoItem item;
        item = new ToDoItem();
        item.setName("hello");
        item.setDetail("hello hello");
        list.add(item);

        item = new ToDoItem();
        item.setName("world");
        item.setDetail("hello hello");
        list.add(item);

        // todo: adapter を自作
        ToDoAdapter adapter = new ToDoAdapter(TestActivity.this);
        adapter.setToDoList(list);

        lvTodo = findViewById(R.id.lv_test);
        lvTodo.setAdapter(adapter);

        lvTodo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                // タップしたアイテムの取得
                Log.d("degug", "Tap: " + i);
                ListView listView = (ListView)parent;
                ToDoItem item = (ToDoItem) listView.getItemAtPosition(i);  // SampleListItemにキャスト

                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setTitle("Tap No. " + i);
                builder.setMessage(item.getName());
                builder.show();
            }
        });

    }
}
