package com.lanny.onlineshoppingcart.product;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.cart.MyDBHelper;
import com.lanny.onlineshoppingcart.wishlist.MyWishListDBHelper;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    String iId, iPname, iQuan, iPrice, iDesc, iImage, selectedQ;
    TextView tvIId, tvIPname, tvIQuan, tvIPrice, tvIDesc;
    ImageView tvIImage;
    Spinner dropdown;
    Button butAddToCart;
    ImageButton butAddToLike;
    public MyDBHelper myDBHelper;
    public MyWishListDBHelper myWishListDBHelper;
    public SQLiteDatabase myDataBase, myWishListDB;


    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item, container, false);


        myDBHelper = new MyDBHelper(getContext());
        myDataBase = myDBHelper.getWritableDatabase();

        myWishListDBHelper = new MyWishListDBHelper(getContext());
        myWishListDB = myWishListDBHelper.getWritableDatabase();

        Bundle b = this.getArguments();
        if (b != null){
            iId = b.getString("pid");
            iPname = b.getString("pname");
            iQuan = b.getString("pquantity");
            iPrice = b.getString("pprice");
            iDesc = b.getString("pdesc");
            iImage = b.getString("image");
        }

        tvIId = view.findViewById(R.id.textViewIId);
        tvIPname = view.findViewById(R.id.textViewIName);
        tvIQuan = view.findViewById(R.id.textViewIQuantity);
        tvIPrice = view.findViewById(R.id.textViewIPrice);
        tvIDesc = view.findViewById(R.id.textViewIDesc);
        tvIImage = view.findViewById(R.id.textViewIImage);
        dropdown = view.findViewById(R.id.spinner1);
        butAddToCart = view.findViewById(R.id.buttonAddtoCart);
        butAddToLike = view.findViewById(R.id.cart_like);

        setItemFragmentView();




        butAddToCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(myDBHelper.ID, tvIId.getText().toString());
                values.put(myDBHelper.NAME, tvIPname.getText().toString());
                values.put(myDBHelper.QUANTITY, selectedQ);
                values.put(myDBHelper.PRICE, tvIPrice.getText().toString());
                values.put(myDBHelper.IMAGE_URL, iImage);

                myDataBase.insert(myDBHelper.TABLE_NAME, null, values);
                //Log.i("xxx", myDataBase.toString());
                Toast.makeText(getContext(), "Add to cart! ", Toast.LENGTH_SHORT).show();
            }
        });

        butAddToLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(myWishListDBHelper.ID, tvIId.getText().toString());
                values.put(myWishListDBHelper.NAME, tvIPname.getText().toString());
                values.put(myWishListDBHelper.QUANTITY, tvIQuan.getText().toString());
                values.put(myWishListDBHelper.PRICE, tvIPrice.getText().toString());
                values.put(myWishListDBHelper.IMAGE_URL, iImage);

                myWishListDB.insert(myWishListDBHelper.TABLE_NAME, null, values);
                Toast.makeText(getContext(), "Add to wish list! ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setItemFragmentView() {

        tvIId.setText(iId);
        tvIPname.setText(iPname);
//        tvIQuan.setText(iQuan);


        int quanNum = Integer.parseInt(iQuan);
        final String[] quantity = new String[quanNum];
        for (int i = 0; i < quanNum; i++){
            quantity[i] = Integer.toString(i+1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, quantity);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setPrompt("Quantity");
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedQ = quantity[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedQ = "1";
            }
        });

        tvIPrice.setText(iPrice);
        tvIDesc.setText(iDesc);

        Picasso.get().load(iImage).fit().into(tvIImage);
    }

}
