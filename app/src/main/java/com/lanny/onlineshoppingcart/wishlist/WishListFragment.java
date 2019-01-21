package com.lanny.onlineshoppingcart.wishlist;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.cart.MyCartViewAdapter;
import com.lanny.onlineshoppingcart.product.Product;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment {
    public static final String TAG = WishListFragment.class.getSimpleName();
    public MyWishListDBHelper myWishListDBHelper;
    public SQLiteDatabase myWishListDB;
    ArrayList<Product> myItemList;
    MyCartViewAdapter myCartViewAdapter;
    RecyclerView cartViewRV;

    private SharedPreferences loginPreferences;
    public SharedPreferences.Editor loginPrefsEditor;

    ArrayList<Integer> priceList;

    public WishListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);


        loginPreferences = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        myWishListDBHelper = new MyWishListDBHelper(getActivity());
        myWishListDB = myWishListDBHelper.getWritableDatabase();

        myItemList = new ArrayList<>();
        cartViewRV = view.findViewById(R.id.recyclerViewCart);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 1);
        cartViewRV.setLayoutManager(gridLayoutManager3);
        cartViewRV.setItemAnimator(new DefaultItemAnimator());

        Cursor cursor = myWishListDB.rawQuery("select * from " + myWishListDBHelper.TABLE_NAME, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                String name = cursor.getString(cursor.getColumnIndex(myWishListDBHelper.NAME));
                String quantity = cursor.getString(cursor.getColumnIndex(myWishListDBHelper.QUANTITY));
                String price = cursor.getString(cursor.getColumnIndex(myWishListDBHelper.PRICE));
                String image_url = cursor.getString(cursor.getColumnIndex(myWishListDBHelper.IMAGE_URL));
                int id = cursor.getInt(cursor.getColumnIndex(myWishListDBHelper.ID));

                Product product = new Product(Integer.toString(id), name, quantity, price, "", image_url);

                myItemList.add(product);


            }

            while (cursor.moveToNext());


        }

        myCartViewAdapter = new MyCartViewAdapter(myItemList);
        cartViewRV.setAdapter(myCartViewAdapter);

        return view;
    }

}
