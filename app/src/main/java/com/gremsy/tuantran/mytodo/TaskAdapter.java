package com.gremsy.tuantran.mytodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/26/2016.
 */
public class TaskAdapter extends ArrayAdapter<TodoTask> {
    public TaskAdapter(Context context, ArrayList<TodoTask> todoTask) {
        super(context, 0, todoTask);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoTask todoTask = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }
        // Lookup view for data population
        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        // Populate the data into the template view using the data object
        tvTask.setText(todoTask.task);
        tvPriority.setText(todoTask.priority);
        // Set priority color
        switch (tvPriority.getText().toString()){
            case "HIGH": tvPriority.setTextColor(Color.RED);
                break;
            case "MEDIUM": tvPriority.setTextColor(Color.GREEN);
                break;
            case "LOW": tvPriority.setTextColor(Color.GRAY);
                break;
            default: break;
        }
        // Return the completed view to render on screen
        return convertView;
    }

}
