package com.zepo_lifestyle.hack_your_life.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;

public class ProductHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public CheckBox complete;
    public ImageView urgent, edit, delete;
    public TextView name, description, price, quantity;

    public ProductHolder(View view) {
        super(view);

        root = view.findViewById(R.id.root);

        complete = view.findViewById(R.id.complete);

        urgent = view.findViewById(R.id.urgent);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);

        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        quantity = view.findViewById(R.id.quantity);
        description = view.findViewById(R.id.description);
    }

}
