package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class TaskDateHolder extends RecyclerView.ViewHolder {

    public TextView date;

    public TaskDateHolder(View view) {
        super(view);

        date = view.findViewById(R.id.date);
    }

}
