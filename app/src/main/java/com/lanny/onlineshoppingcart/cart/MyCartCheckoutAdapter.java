package com.lanny.onlineshoppingcart.cart;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;

import java.util.ArrayList;
import java.util.List;

public class MyCartCheckoutAdapter extends RecyclerView.Adapter<MyCartCheckoutAdapter.MyViewHolder>{
    public View view;
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;
    List<Integer> mProd;


    public MyCartCheckoutAdapter(ArrayList<Integer> myProjects){
        this.mProd = myProjects;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView total;

        public Button butCheckOut;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            total = itemView.findViewById(R.id.textViewCartTotal);

            myDBHelper = new MyDBHelper(itemView.getContext());
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_checkout_item, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final int position = i;
        final Integer list = mProd.get(i);

//        myViewHolder.pid.setText((list.getId()));
        myViewHolder.total.setText(list.toString());

//        myViewHolder.pquantity.setText(list.getQuantity());

//        myViewHolder.pdesc.setText(list.getDesc());



        myViewHolder.butCheckOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return 1;
    }

}
