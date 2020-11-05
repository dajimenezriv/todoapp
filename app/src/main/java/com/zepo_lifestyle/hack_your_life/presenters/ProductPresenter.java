package com.zepo_lifestyle.hack_your_life.presenters;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.classes.Product;
import com.zepo_lifestyle.hack_your_life.holders.ProductHolder;
import com.zepo_lifestyle.hack_your_life.models.ProductModel;
import com.zepo_lifestyle.hack_your_life.views.MainView;
import com.zepo_lifestyle.hack_your_life.views.NewProductView;

public class ProductPresenter extends Presenter {

    private double total_price;
    private ProductModel model;

    ProductPresenter(MainView view) {
        super(view);

        model = new ProductModel(view);

        arrayAndBottomText();
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private void arrayAndBottomText() {
        int number_pending_products = 0;

        /* Array */

        for (Product product : model.getProducts()) {
            array.add(product);
            if (product.getPending()) number_pending_products++;
        }

        /* Bottom Text */

        for (int i = 0; i < number_pending_products; i++) {
            Product product = (Product) array.get(i);
            total_price += product.getPrice() * product.getQuantity();
        }
    }

    public void init() {
        /* Title */

        view.setTitle(R.string.products);

        /* Bottom Text */

        updateBottomText();
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    @NonNull
    public ProductHolder onCreateViewHolder(ViewGroup parent, int view_type) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, final int pos) {
        /* Holder */

        final ProductHolder h = (ProductHolder) rv_holder;

        /* Get Item */

        final Product product = (Product) array.get(pos);

        /* Values */

        String name = product.getSortedName();
        String description = product.getSortedDescription();
        double price = product.getPrice();
        int quantity = product.getQuantity();
        boolean urgent = product.getUrgent();
        boolean pending = product.getPending();

        h.complete.setChecked(pending);
        h.name.setText(name);
        h.description.setText(description);
        h.price.setText(view.getString(R.string.price, price));
        h.quantity.setText(view.getString(R.string.quantity, quantity));

        /* Exceptions */

        if (urgent) h.urgent.setVisibility(View.VISIBLE);
        else h.urgent.setVisibility(View.GONE);
        if (price == 0) h.price.setVisibility(View.GONE);
        else h.price.setVisibility(View.VISIBLE);
        if (pending) {
            h.quantity.setVisibility(View.VISIBLE);
            h.description.setVisibility(View.VISIBLE);
        } else {
            h.quantity.setVisibility(View.GONE);
            h.description.setVisibility(View.GONE);
        }
        if (description.isEmpty()) h.description.setVisibility(View.GONE);

        /* Listeners */

        h.edit.setOnClickListener((View v) -> {
            position = pos;
            edit();
        });

        h.delete.setOnClickListener((View v) -> {
            position = pos;
            delete();
        });

        h.root.setOnClickListener((View v) -> {
            position = pos;
            if (pending) quantityPicker();
            else edit();
        });

        h.complete.setOnClickListener((View v) -> {
            position = pos;
            invertPending();
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    /*
     * Bottom Text
     *
     *
     *
     * */

    public void updateBottomText() {
        view.setBottomText(view.getString(R.string.total_price, total_price));
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add() {
        view.startActivityForResult(new Intent(view, NewProductView.class), 0);
    }

    public void saveAdd(long id) {
        /* Get Item */

        Product product = model.getProductById(id);

        /* Number Pending Products */

        if (product.getPending()) total_price -= product.getPrice() * product.getQuantity();

        /* Modify Array */

        array.add(product);

        /* Bottom Text */

        updateBottomText();
    }

    public void edit() {
        view.startActivityForResult(new Intent(view, NewProductView.class).putExtra("id", array.get(position).getId()), 1);
    }

    public void saveEdit(long id) {
        /* Get Item */

        Product previous_product = (Product) array.get(position);
        Product product = model.getProductById(id);

        /* Bottom Text */

        boolean previous_pending = previous_product.getPending();
        double previous_price = previous_product.getPrice();
        boolean pending = product.getPending();
        double price = product.getPrice();
        int quantity = product.getQuantity();

        if (previous_pending && !pending) total_price -= previous_price * quantity;
        else if (!previous_pending && pending) total_price += price * quantity;
        else if (pending) total_price += (price - previous_price) * quantity;

        updateBottomText();

        /* Modify Array */

        array.updateItemAt(position, product);
    }

    public void delete() {
        /* Get Item */

        Product product = (Product) array.get(position);
        last_deleted = product;

        /* Modify Array */

        array.removeItemAt(position);

        /* Bottom Text */

        if (product.getPending()) total_price -= product.getPrice() * product.getQuantity();

        updateBottomText();

        /* Private Functions */

        view.showUndo();

        /* Modify DB */

        model.deleteProduct(product);
    }

    public void undo() {
        /* Update DB */

        model.newProduct((Product) last_deleted);

        /* Modify Array */

        array.add(last_deleted);

        /* Bottom Text */

        updateBottomText();
    }

    /*
     * Complete
     *
     *
     *
     * */

    private void invertPending() {
        Product product = (Product) array.get(position);

        /* Private Functions */

        product.invertPending();

        /* Bottom Text */

        if (product.getPending()) total_price += product.getQuantity() * product.getPrice();
        else total_price -= product.getQuantity() * product.getPrice();

        updateBottomText();

        /* Private Functions */

        product.setQuantity(1);

        /* Modify Array */

        array.updateItemAt(position, product);

        /* Modify DB */

        model.updateProduct(product);
    }

    /*
     * Pickers
     *
     *
     *
     * */

    private void quantityPicker() {
        Product product = (Product) array.get(position);

        final double price = product.getPrice();
        final double previous_total_price = total_price - price * product.getQuantity();

        int previous_quantity = product.getQuantity();

        NumberPicker np = new NumberPicker(view);
        np.setMaxValue(9);
        np.setMinValue(1);
        np.setValue(previous_quantity);
        np.setOnValueChangedListener((NumberPicker picker, int old_value, int new_value) -> {
            /* Private Functions */

            product.setQuantity(new_value);

            /* Modify Array */

            array.updateItemAt(position, product);

            /* Bottom Text */

            total_price = previous_total_price + price * new_value;
            updateBottomText();

            /* Modify DB */

            model.updateProduct(product);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(view, android.R.style.Theme_Material_Light_Dialog);
        builder.setView(np);
        Window window = builder.show().getWindow();
        if (window != null)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
