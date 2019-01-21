package com.lanny.onlineshoppingcart.wishlist;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.product.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyWishListViewAdapter extends RecyclerView.Adapter<MyWishListViewAdapter.MyViewHolder>{
    public View view;
    public MyWishListDBHelper myWishListDBHelper;
    public SQLiteDatabase myDataBase;
    List<Product> mProd;


    public MyWishListViewAdapter(ArrayList<Product> myProjects){
        this.mProd = myProjects;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pid, pname, pquantity, pprice, pdesc;
        public ImageView pimage;
        public CardView pbg;
        public ImageButton butDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            //pid = itemView.findViewById(R.id.textViewCartId);
            pname = itemView.findViewById(R.id.textViewWishName);
            pquantity = itemView.findViewById(R.id.textViewWishQuantity);
            pprice = itemView.findViewById(R.id.textViewWishPrice);
//            pdesc = itemView.findViewById(R.id.textViewCartDesc);
            pimage = itemView.findViewById(R.id.textViewWishImage);
            butDelete = itemView.findViewById(R.id.buttonWishDelete);
            pbg = itemView.findViewById(R.id.card_view2);

            myWishListDBHelper = new MyWishListDBHelper(itemView.getContext());
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wish_list_recycler_item, viewGroup, false);


        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final int position = i;
        final Product list = mProd.get(i);

//        myViewHolder.pid.setText((list.getId()));
        myViewHolder.pname.setText(list.getPname());
//        myViewHolder.pquantity.setText("Quantity: " + list.getQuantity());
        myViewHolder.pprice.setText("Price: $"+list.getPrice());
//        myViewHolder.pdesc.setText(list.getDesc());

        Picasso.get().load(list.getImage()).fit().into(myViewHolder.pimage);


        myViewHolder.butDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                myDataBase = myWishListDBHelper.getWritableDatabase();
                myDataBase.delete(myWishListDBHelper.TABLE_NAME,"ID" + " = " + list.getId(), null);

                mProd.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mProd.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProd.size();
    }

}
