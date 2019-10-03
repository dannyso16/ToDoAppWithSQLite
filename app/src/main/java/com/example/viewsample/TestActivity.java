package com.example.viewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    ListView lvTodo;
    ArrayList<ToDoItem> toDoList;
    ToDoAdapter adapter;

    private void addItem(ArrayList<ToDoItem> toDoList, String name, String detail) {
        ToDoItem item = new ToDoItem();
        item.setName(name);
        item.setDetail(detail);
        toDoList.add(item);
    }

    private boolean removeItem(ArrayList<ToDoItem> toDoList) {
        if (toDoList.size() >= 1) {
            toDoList.remove(0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*
         * button listener
         */
        Button addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("listener", "Button(ADD) is clicked");
                addItem(toDoList, "hello", "world");
                adapter.notifyDataSetChanged();
                Log.d("debug", "toDoList.size: " + toDoList.size());
            }
        });

        Button removeBtn = findViewById(R.id.btn_remove);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("listener", "Button(ADD) is clicked");
                removeItem(toDoList);
                adapter.notifyDataSetChanged();
                Log.d("debug", "toDoList.size: " + toDoList.size());
                Toast.makeText(TestActivity.this,
                        "First To Do is removed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        toDoList = new ArrayList<>();
        ToDoItem item;
        item = new ToDoItem();
        item.setName("hello");
        item.setDetail("hello hello");
        toDoList.add(item);

        // todo: adapter を自作
        adapter = new ToDoAdapter(TestActivity.this);
        adapter.setToDoList(toDoList);

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
