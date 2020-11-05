package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class DaysOfWeekHolder extends RecyclerView.ViewHolder {

    public TextView day;

    public DaysOfWeekHolder(View view) {
        super(view);

        day = view.findViewById(R.id.day);
    }

}