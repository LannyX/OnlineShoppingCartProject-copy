package com.lanny.onlineshoppingcart.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyProductListAdapter extends RecyclerView.Adapter<MyProductListAdapter.MyViewHolder>{
    public View view;

    List<Product> mProd;


    public MyProductListAdapter(ArrayList<Product> myProjects){
        this.mProd = myProjects;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pid, pname, pquantity, pprice, pdesc;
        public ImageView pimage;
        public CardView pbg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            pid = itemView.findViewById(R.id.textViewPId);
            pname = itemView.findViewById(R.id.textViewPName);
            pquantity = itemView.findViewById(R.id.textViewPQuantity);
            pprice = itemView.findViewById(R.id.textViewPPrice);
            pdesc = itemView.findViewById(R.id.textViewPDesc);
            pimage = itemView.findViewById(R.id.textViewPImage);
            pbg = itemView.findViewById(R.id.card_view2);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_recycler_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final Product list = mProd.get(i);

//        myViewHolder.pid.setText((list.getId()));
        myViewHolder.pname.setText(list.getPname());
//        myViewHolder.pquantity.setText(list.getQuantity());
        myViewHolder.pprice.setText(list.getPrice());
        myViewHolder.pdesc.setText(list.getDesc());

        Picasso.get().load(list.getImage()).into(myViewHolder.pimage);


        myViewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFragment itemFragment = new ItemFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Bundle bundle = new Bundle();
                bundle.putString("pid", list.getId());
                bundle.putString("pname", list.getPname());
                bundle.putString("pquantity", list.getQuantity());
                bundle.putString("pprice", list.getPrice());
                bundle.putString("pdesc", list.getDesc());
                bundle.putString("image", list.getImage());

                itemFragment.setArguments(bundle);
                FragmentManager fm = activity.getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.bottom_frame_layout, itemFragment).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mProd.size();
    }

}
