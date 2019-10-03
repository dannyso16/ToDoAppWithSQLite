package com.example.viewsample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<ToDoItem> toDoList;

    public ToDoAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setToDoList(ArrayList<ToDoItem> toDoList) {
        this.toDoList = toDoList;
    }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return toDoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(
                R.layout.checkbox_tv_star, parent, false);

        // TextView に文字列を設定
        ((TextView)convertView.findViewById(R.id.tv_in_lv)).setText(
                toDoList.get(position).getName());

        // CheckBox にリスナを設定
        final CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.cb_done_in_lv);
        cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // チェック状態が変更された時の処理を記述
                if (cbDone.isChecked()) {
                    Log.d("listener", "CheckBox(Done) is checked");
                } else {
                    Log.d("listener", "CheckBox(Done) is Unchecked");

                }
            }
        });

        final CheckBox cbStar = (CheckBox) convertView.findViewById(R.id.cb_star_in_lv);
        cbStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // チェック状態が変更された時の処理を記述
                if (cbDone.isChecked()) {
                    Log.d("listener", "CheckBox(Star) is checked");
                } else {
                    Log.d("listener", "CheckBox(Star) is Unchecked");

                }
            }
        });

        return convertView;
    }
}
