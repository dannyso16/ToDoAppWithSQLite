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

        List<String> titles = new ArrayList<>();
        titles.add("hello");
        titles.add("world");
        titles.add("android");

        // todo: adapter を自作

//        ArrayAdapter adapter = new ArrayAdapter<>(TestActivity.this,
//                R.layout.checkbox_tv_star, titles);
//        lvTodo = (ListView)findViewById(R.id.lv_test);
//        lvTodo.setAdapter(adapter);

    }
}
