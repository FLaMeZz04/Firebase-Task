package com.flamezz.firebasefetchdata;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> implements ItemHelper {



    private ArrayList<Pojo> arrayList;
    private OnClickListener listener;
    private Context mContext;
    private DataAdapter.DataViewHolder viewHolder;


    private DragListener dragListener;
    public DataAdapter(ArrayList<Pojo> list,Context context,DragListener dragListener,OnClickListener listener)
    {
       this.arrayList=list;
        this.mContext=context;
        this.dragListener=dragListener;
        this.listener=listener;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_recyclerview,viewGroup,false);
        DataViewHolder viewHolder = new DataViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        dataViewHolder.onBind(i);
        this.viewHolder=dataViewHolder;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition)
        {
            for(int i=fromPosition;i<toPosition;i++)
            {
                Collections.swap(arrayList,i,i+1);
            }

        }
        else
        {
            for(int i=fromPosition;i>toPosition;i--)
            {
                Collections.swap(arrayList,i,i-1);
            }
        }
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onRowSelected(DataViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onRowClear(DataViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.WHITE);
    }



    class DataViewHolder extends BaseViewHolder
    {

        private View rowView;

        @BindView(R.id.order)
        TextView order;

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.relativeLayout)
        RelativeLayout relativeLayout;

        @BindView(R.id.drag)
        ImageView drag;

        public DataViewHolder(View view) {
            super(view);
            rowView = view;
            ButterKnife.bind(this,view);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            order.setText(String.valueOf(arrayList.get(position).getOrder()));
            name.setText(arrayList.get(position).getName());
            Picasso.get().load(arrayList.get(position).getImage()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(arrayList.get(position).getName(),arrayList.get(position).getOrder());
                }
            });
            drag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() ==
                            MotionEvent.ACTION_DOWN) {
                        dragListener.startDragListener(viewHolder);
                    }
                    return false;

                }
            });


        }
    }
}
