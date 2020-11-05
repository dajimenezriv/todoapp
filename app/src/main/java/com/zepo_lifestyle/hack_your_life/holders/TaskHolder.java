package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class TaskHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public CheckBox complete;
    public ImageView urgent, edit, delete, repeat_icon, time_icon;
    public TextView name, description, date, time;

    public TaskHolder(View view) {
        super(view);

        root = view.findViewById(R.id.root);

        complete = view.findViewById(R.id.complete);

        urgent = view.findViewById(R.id.urgent);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);
        repeat_icon = view.findViewById(R.id.repeat_icon);
        time_icon = view.findViewById(R.id.time_icon);

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
    }

}
