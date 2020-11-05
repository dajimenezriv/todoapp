package com.zepo_lifestyle.hack_your_life.presenters;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.classes.Product;
import com.zepo_lifestyle.hack_your_life.models.ProductModel;
import com.zepo_lifestyle.hack_your_life.views.NewProductView;

public class ProductNewPresenter {

    private NewProductView view;
    private ProductModel model;

    private long id;
    private Product product;

    public ProductNewPresenter(NewProductView view, long id) {
        this.view = view;
        this.id = id;

        model = new ProductModel(view);

        if (id == -1) product = Product.createProduct(null, null);
        else {
            product = model.getProductById(id);
            double price = product.getPrice();
            view.setTitle(view.getString(R.string.edit_product));
            view.setName(product.getName());
            view.setDescription(product.getDescription());
            view.setPrice((price == 0) ? "" : Double.toString(product.getPrice()));
            view.setUrgent(product.getUrgent());
            view.setPending(product.getPending());
        }
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(String name, String description, String price, boolean urgent, boolean pending) {
        if (name.equals("")) {
            view.showMessage(R.string.error_name_empty);
            return;
        }

        double p = 0;

        if (!price.equals("")) {
            try {
                p = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                view.showMessage(R.string.error_price_number);
                return;
            }
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(p);
        product.setUrgent(urgent);
        product.setPending(pending);

        saveChanges();
    }

    /*
     * Save Changes
     *
     *
     *
     * */

    private void saveChanges() {
        if (id == -1) view.saveChanges(model.newProduct(product));
        else {
            model.updateProduct(product);
            view.saveChanges(product.getId());
        }
    }

}