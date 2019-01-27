package com.flamezz.firebasefetchdata;

public interface ItemHelper {

    void onRowMoved(int fromPosition,int toPosition);


    void onRowSelected(DataAdapter.DataViewHolder viewHolder);

    void onRowClear(DataAdapter.DataViewHolder viewHolder);


}
