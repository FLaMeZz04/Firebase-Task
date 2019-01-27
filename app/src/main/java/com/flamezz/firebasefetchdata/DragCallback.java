package com.flamezz.firebasefetchdata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DragCallback extends ItemTouchHelper.Callback {



    private ItemHelper itemHelper;


    public DragCallback(ItemHelper itemHelper)
    {
        this.itemHelper=itemHelper;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int flags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(flags,0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        itemHelper.onRowMoved(viewHolder.getAdapterPosition(),viewHolder1.getAdapterPosition());
        return true;
    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof DataAdapter.DataViewHolder) {
                DataAdapter.DataViewHolder viewHolder1 =
                        (DataAdapter.DataViewHolder) viewHolder;
                itemHelper.onRowSelected(viewHolder1);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof DataAdapter.DataViewHolder) {
            DataAdapter.DataViewHolder myViewHolder=
                    (DataAdapter.DataViewHolder) viewHolder;
            itemHelper.onRowClear(myViewHolder);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

}



