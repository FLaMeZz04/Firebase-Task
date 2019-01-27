package com.flamezz.firebasefetchdata;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.supercharge.shimmerlayout.ShimmerLayout;


public class MainActivity extends AppCompatActivity implements DragListener {

    private ArrayList<Pojo> arrayList;
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.saveButton)
    Button saveButton;
    ItemTouchHelper itemTouchHelper;
    @BindView(R.id.shimmerLayout)
    ShimmerLayout shimmerLayout;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFirebase();
        shimmerLayout.startShimmerAnimation();
        loadData();
    }

    public void initFirebase()
    {
        databaseReference = FirebaseDatabase.getInstance()
                        .getReference();
    }


    public void loadData() {
      arrayList  = new ArrayList<>();
        databaseReference.orderByChild("order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Pojo pojo = ds.getValue(Pojo.class);
                    arrayList.add(pojo);

                }
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                initAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getDetails());
            }
        });

    }

   void initAdapter()
   {

      DataAdapter dataAdapter = new DataAdapter(arrayList, getApplicationContext(),this, new OnClickListener() {
           @Override
           public void onClick(String name, int order) {
               Toasty.info(getApplicationContext(),name+"\tclicked",3000,true).show();
           }
       });
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       recyclerView.setAdapter(dataAdapter);
       DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
               new LinearLayoutManager(getApplicationContext()).getOrientation());
       recyclerView.addItemDecoration(dividerItemDecoration);
       ItemTouchHelper.Callback callback =new DragCallback(dataAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
       itemTouchHelper.attachToRecyclerView(recyclerView);
   }


   @OnClick(R.id.saveButton)
    void saveData()
   {
       databaseReference.setValue(arrayList);
       Toasty.success(getApplicationContext(),"Data saved ",3000,true).show();
   }

    @Override
    public void startDragListener(DataAdapter.DataViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}

