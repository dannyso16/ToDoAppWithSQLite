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
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater = null;
    private ArrayList<ToDoItem> toDoList;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = layoutInflater.inflate(
                R.layout.checkbox_tv_star, parent, false);

        // TextView に文字列を設定
        ((TextView)convertView.findViewById(R.id.tv_in_lv)).setText(
                toDoList.get(position).getName());
        boolean isStarred = toDoList.get(position).getIsStarred().equals("1");
        ((CheckBox)convertView.findViewById(R.id.cb_star_in_lv)).setChecked(isStarred);

        // CheckBox にリスナを設定
        final CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.cb_done_in_lv);
        cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // チェック状態が変更された時の処理を記述
                if (cbDone.isChecked()) {
                    Log.d("listener", "CheckBox(Done) is checked");
                    ((ListView) parent).performItemClick(cbDone, position, R.id.cb_done_in_lv);
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
                if (cbStar.isChecked()) {
                    Log.d("listener", "CheckBox(Star) is checked");
                    toDoList.get(position).setIsStarred("1");
                    ((ListView) parent).performItemClick(cbStar, position, R.id.cb_star_in_lv);
                } else {
                    Log.d("listener", "CheckBox(Star) is Unchecked");
                    toDoList.get(position).setIsStarred("0");

                }
            }
        });

        return convertView;
    }
}
