package com.zepo_lifestyle.hack_your_life.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.zepo_lifestyle.hack_your_life.classes.Product;

import java.util.ArrayList;

public class ProductModel extends DBHelper {

    public ProductModel(Context context) {
        super(context);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private ContentValues getContentValues(Product p) {
        ContentValues cv = new ContentValues();
        if (p.getId() != -1) cv.put("id", p.getId());
        cv.put("name", p.getName());
        cv.put("description", p.getDescription());
        cv.put("price", p.getPrice());
        cv.put("quantity", p.getQuantity());
        cv.put("urgent", p.getUrgent() ? 1 : 0);
        cv.put("pending", p.getPending() ? 1 : 0);

        return cv;
    }

    private Product getProduct(Cursor c) {
        long id = c.getLong(c.getColumnIndex("id"));
        String name = c.getString(c.getColumnIndex("name"));
        String description = c.getString(c.getColumnIndex("description"));
        double price = c.getDouble(c.getColumnIndex("price"));
        int quantity = c.getInt(c.getColumnIndex("quantity"));
        boolean urgent = (c.getInt(c.getColumnIndex("urgent")) == 1);
        boolean pending = (c.getInt(c.getColumnIndex("pending")) == 1);

        return new Product(id, name, description, price, quantity, urgent, pending);
    }

    /*
     * Add
     *
     *
     *
     * */

    public long newProduct(Product h) {
        try {
            return getWritableDatabase().insert(
                    "products",
                    null,
                    getContentValues(h));
        } catch (Exception e) {
            Log.i(TAG, e.toString());
            return -1;
        }
    }

    /*
     * Update
     *
     *
     *
     * */

    public void updateProduct(Product p) {
        try {
            getWritableDatabase().update(
                    "products",
                    getContentValues(p),
                    "id = ?",
                    new String[]{Long.toString(p.getId())});
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /*
     * Delete
     *
     *
     *
     * */

    public void deleteProduct(Product p) {
        try {
            getWritableDatabase().delete(
                    "products",
                    "id = ?",
                    new String[]{Long.toString(p.getId())});
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /*
     * Getters
     *
     *
     *
     * */

    public ArrayList<Product> getProducts() {
        ArrayList<Product> array_products = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
                "products",
                null, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array_products.add(getProduct(cursor));
            cursor.moveToNext();
        }

        return array_products;
    }

    public Product getProductById(long id) {
        Cursor cursor = getReadableDatabase().query(
                "products",
                null,
                "id = ?",
                new String[]{Long.toString(id)},
                null, null, null);

        if (cursor.moveToFirst()) return getProduct(cursor);

        return null;
    }

}