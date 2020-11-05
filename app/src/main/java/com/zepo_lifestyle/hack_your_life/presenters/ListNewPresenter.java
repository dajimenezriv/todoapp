package com.zepo_lifestyle.hack_your_life.presenters;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.classes.List;
import com.zepo_lifestyle.hack_your_life.models.ListModel;
import com.zepo_lifestyle.hack_your_life.views.NewListView;

public class ListNewPresenter {

    private NewListView view;
    private ListModel model;

    private long id;
    private List list;

    public ListNewPresenter(NewListView view, long id) {
        this.view = view;
        this.id = id;

        model = new ListModel(view);

        if (id == -1) list = List.createList(null, null);
        else {
            list = model.getListById(id);
            view.setTitle(view.getString(R.string.edit_list));
            view.setName(list.getName());
        }
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(String name) {
        if (name.equals("")) {
            view.showMessage(R.string.error_name_empty);
            return;
        }

        list.setName(name);

        saveChanges();
    }

    /*
     * Save Changes
     *
     *
     *
     * */

    private void saveChanges() {
        if (id == -1) view.saveChanges(model.newList(list));
        else {
            model.updateList(list);
            view.saveChanges(list.getId());
        }
    }

}