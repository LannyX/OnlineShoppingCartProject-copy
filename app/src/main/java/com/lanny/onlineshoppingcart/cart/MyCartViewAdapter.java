package com.lanny.onlineshoppingcart.cart;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.product.Product;
import com.lanny.onlineshoppingcart.wishlist.MyWishListDBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCartViewAdapter extends RecyclerView.Adapter<MyCartViewAdapter.MyViewHolder>{
    public View view;
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;
    public SQLiteDatabase myWishDB;
    public MyWishListDBHelper myWishListDBHelper;
    List<Product> mProd;


    public MyCartViewAdapter(ArrayList<Product> myProjects){
        this.mProd = myProjects;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pid, pname, pquantity, pprice, pdesc;
        public ImageView pimage;
        public CardView pbg;
        public ImageButton butDelete, butLike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            //pid = itemView.findViewById(R.id.textViewCartId);
            pname = itemView.findViewById(R.id.textViewCartName);
            pquantity = itemView.findViewById(R.id.textViewCartQuantity);
            pprice = itemView.findViewById(R.id.textViewCartPrice);
//            pdesc = itemView.findViewById(R.id.textViewCartDesc);
            pimage = itemView.findViewById(R.id.textViewCartImage);
            butDelete = itemView.findViewById(R.id.buttonDelete);
            butLike = itemView.findViewById(R.id.buttonLike);
            pbg = itemView.findViewById(R.id.card_view2);

            myDBHelper = new MyDBHelper(itemView.getContext());
            myWishListDBHelper = new MyWishListDBHelper(itemView.getContext());
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_recycler_item, viewGroup, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final int position = i;
        final Product list = mProd.get(i);

//        myViewHolder.pid.setText((list.getId()));
        myViewHolder.pname.setText(list.getPname());
//        myViewHolder.pquantity.setText("Quantity: " + list.getQuantity().toString() + " ( Unitprice: $"+ list.getPrice().toString() +" )");
        //int total = Integer.parseInt(list.getQuantity());
        Log.i("asd", list.getQuantity());
        int q = Integer.parseInt(list.getPrice().toString());

        myViewHolder.pquantity.setText("Quantity: " + list.getQuantity().toString() + " ( Unitprice: $"+ list.getPrice().toString() +" )");

//        myViewHolder.pdesc.setText(list.getDesc());

        Picasso.get().load(list.getImage()).fit().into(myViewHolder.pimage);

        myViewHolder.butDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                myDataBase = myDBHelper.getWritableDatabase();
                myDataBase.delete(myDBHelper.TABLE_NAME,"ID" + " = " + list.getId(), null);

                mProd.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mProd.size());
            }
        });

        myViewHolder.butLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                myDataBase = myDBHelper.getWritableDatabase();
                myDataBase.delete(myDBHelper.TABLE_NAME,"ID" + " = " + list.getId(), null);

                myWishDB = myWishListDBHelper.getWritableDatabase();
                myWishDB.insert(myWishListDBHelper.TABLE_NAME,"ID" + " = " + list.getId(), null);

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
