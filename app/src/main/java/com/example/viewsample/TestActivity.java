package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    }
}
