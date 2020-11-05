package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class ListHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public TextView name;
    public ImageView edit, delete, more;

    public ListHolder(View view) {
        super(view);

        root = view.findViewById(R.id.root);

        name = view.findViewById(R.id.name);

        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);
        more = view.findViewById(R.id.more);
    }


}
