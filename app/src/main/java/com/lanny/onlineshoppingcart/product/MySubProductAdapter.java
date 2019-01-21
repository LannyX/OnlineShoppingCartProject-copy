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

import java.util.List;

public class MySubProductAdapter extends RecyclerView.Adapter<MySubProductAdapter.MyViewHolder>{
    public View view;

    List<ProductCategories> mProd;


    public MySubProductAdapter(List<ProductCategories> myProjects){
        this.mProd = myProjects;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView scategoryId, scategoryName, scategoryDescription;
        public ImageView scategoryImage;
        public CardView sbg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            scategoryId = itemView.findViewById(R.id.textViewCategoryId);
            scategoryName = itemView.findViewById(R.id.textViewCategoryName);
            scategoryImage = itemView.findViewById(R.id.textViewCategoryImage);
            scategoryDescription = itemView.findViewById(R.id.textViewCategoryDescription);
            sbg = itemView.findViewById(R.id.card_view);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_recycler_view_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final ProductCategories list = mProd.get(i);
        //myViewHolder.categoryId.setText((list.getCategoryId()));
        myViewHolder.scategoryName.setText((list.getCategoryName()));

        //myViewHolder.categoryImage.setImageResource((list.getCategoryImage()));
//        Picasso.get().load(list.getCategoryImage()).fit().centerInside().into(myViewHolder.categoryImage);
        Picasso.get().load(list.getCategoryImage()).into(myViewHolder.scategoryImage);

        //myViewHolder.categoryDescription.setText((list.getCategoryDescription()));


        myViewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListFragment productListFragment = new ProductListFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

//                Log.i("xxx", list.getCategoryId());
                Bundle bundle = new Bundle();
                bundle.putString("id", list.getCategoryId());
                bundle.putString("scid", list.getScid());
                productListFragment.setArguments(bundle);
                FragmentManager fm = activity.getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.bottom_frame_layout, productListFragment).addToBackStack(null).commit();

            }
        });
    }


    @Override
    public int getItemCount() {
        return mProd.size();
    }

}
