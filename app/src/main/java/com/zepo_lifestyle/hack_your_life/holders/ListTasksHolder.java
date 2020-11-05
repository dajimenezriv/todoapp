package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class ListTasksHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public ImageView urgent, repeat_icon, time_icon;
    public TextView name, description, date, time;

    public ListTasksHolder(View view) {
        super(view);

        root = view.findViewById(R.id.root);

        urgent = view.findViewById(R.id.urgent);
        repeat_icon = view.findViewById(R.id.repeat_icon);
        time_icon = view.findViewById(R.id.time_icon);

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
    }

}
