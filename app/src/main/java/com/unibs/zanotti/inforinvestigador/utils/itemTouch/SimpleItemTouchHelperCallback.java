package com.unibs.zanotti.inforinvestigador.utils.itemTouch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private boolean longPressDragEnabled;
    private boolean itemViewSwipeEnabled;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter,
                                         boolean longPressDragEnabled,
                                         boolean itemViewSwipeEnabled) {
        mAdapter = adapter;
        this.longPressDragEnabled = longPressDragEnabled;
        this.itemViewSwipeEnabled = itemViewSwipeEnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return longPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return itemViewSwipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}