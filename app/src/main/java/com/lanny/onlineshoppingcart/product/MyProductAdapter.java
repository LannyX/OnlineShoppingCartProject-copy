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

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>{
    public View view;

    List<ProductCategories> mProd;


    public MyProductAdapter(List<ProductCategories> myProjects){
        this.mProd = myProjects;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryId, categoryName, categoryDescription;
        public ImageView categoryImage;
        public CardView bg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            categoryId = itemView.findViewById(R.id.textViewCategoryId);
            categoryName = itemView.findViewById(R.id.textViewCategoryName);
            categoryImage = itemView.findViewById(R.id.textViewCategoryImage);
            categoryDescription = itemView.findViewById(R.id.textViewCategoryDescription);
            bg = itemView.findViewById(R.id.card_view);

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
        myViewHolder.categoryName.setText((list.getCategoryName()));

        //myViewHolder.categoryImage.setImageResource((list.getCategoryImage()));
//        Picasso.get().load(list.getCategoryImage()).fit().centerInside().into(myViewHolder.categoryImage);
        Picasso.get().load(list.getCategoryImage()).into(myViewHolder.categoryImage);

        //myViewHolder.categoryDescription.setText((list.getCategoryDescription()));


        myViewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

//                Log.i("xxx", list.getCategoryId());
                Bundle bundle = new Bundle();
                bundle.putString("id", list.getCategoryId());
                subCategoryFragment.setArguments(bundle);
                FragmentManager fm = activity.getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.bottom_frame_layout, subCategoryFragment).addToBackStack(null).commit();


                //v.getContext().startActivity(new Intent(v.getContext(), SubCategoryFragment.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mProd.size();
    }

}
