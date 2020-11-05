package com.zepo_lifestyle.hack_your_life.presenters;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.zepo_lifestyle.hack_your_life.classes.ElementRecyclerView;
import com.zepo_lifestyle.hack_your_life.views.MainView;

public abstract class Presenter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainView view;

    int position;
    ElementRecyclerView last_deleted;

    SortedList<ElementRecyclerView> array;

    Presenter(MainView view) {
        this.view = view;

        createArray();
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    void createArray() {
        array = new SortedList<>(ElementRecyclerView.class, new SortedList.Callback<ElementRecyclerView>() {
            @Override
            public int compare(ElementRecyclerView o1, ElementRecyclerView o2) {
                return o1.getSortedString().compareTo(o2.getSortedString());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ElementRecyclerView old_item, ElementRecyclerView new_item) {
                return old_item.toString().equals(new_item.toString());
            }

            @Override
            public boolean areItemsTheSame(ElementRecyclerView item1, ElementRecyclerView item2) {
                return item1.getId() == item2.getId();
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemInserted(position);
                notifyItemRangeChanged(position, array.size());
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, array.size());
            }

            @Override
            public void onMoved(int from_position, int to_position) {
                notifyItemMoved(from_position, to_position);
            }
        });
    }

    abstract void init();

    /*
     * Actions
     *
     *
     *
     * */

    /*
     * RecyclerView
     *
     *
     *
     * */

    /*
     * Bottom Text
     *
     *
     *
     * */

    abstract void updateBottomText();

    /*
     * Options
     *
     *
     *
     * */

    abstract void add();

    abstract void saveAdd(long id);

    abstract void edit();

    abstract void saveEdit(long id);

    abstract void delete();

    abstract void undo();

    /*
     * Complete
     *
     *
     *
     * */

    /*
     * Pickers
     *
     *
     *
     * */
}
