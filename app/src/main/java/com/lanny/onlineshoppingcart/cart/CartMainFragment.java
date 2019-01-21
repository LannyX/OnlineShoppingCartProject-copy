package com.lanny.onlineshoppingcart.cart;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;
import com.lanny.onlineshoppingcart.order.OrderInfoFragment;
import com.lanny.onlineshoppingcart.product.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartMainFragment extends Fragment {
    public static final String TAG = CartMainFragment.class.getSimpleName();
    public MyDBHelper myDBHelper;
    public SQLiteDatabase myDataBase;
    ArrayList<Product> myItemList;
    MyCartViewAdapter myCartViewAdapter;
    RecyclerView cartViewRV;
    Button butCheckout, butApply;
    int id, cartTotalPrice = 0, discountInt;
    private SharedPreferences loginPreferences;
    public SharedPreferences.Editor loginPrefsEditor;
    String api_key, user_id;
    TextView beforeCoupon, discount, total, coupon;
    ArrayList<Integer> priceList;

    public CartMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_main, container, false);


        loginPreferences = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        user_id = loginPreferences.getString("spId", "");
        api_key = loginPreferences.getString("spApiKeys", "");

        myDBHelper = new MyDBHelper(getActivity());
        myDataBase = myDBHelper.getWritableDatabase();

        myItemList = new ArrayList<>();
        cartViewRV = view.findViewById(R.id.recyclerViewCart);
        butCheckout = view.findViewById(R.id.buttonCheckout);
        butApply = view.findViewById(R.id.applyCoupon);
        total = view.findViewById(R.id.textViewTotal);
        coupon = view.findViewById(R.id.coupon);
        beforeCoupon = view.findViewById(R.id.textViewbeforeCoupon);
        discount = view.findViewById(R.id.textViewDiscount);


        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 1);
        cartViewRV.setLayoutManager(gridLayoutManager3);
        cartViewRV.setItemAnimator(new DefaultItemAnimator());

        Cursor cursor = myDataBase.rawQuery("select * from " + myDBHelper.TABLE_NAME, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                String name = cursor.getString(cursor.getColumnIndex(myDBHelper.NAME));
                String quantity = cursor.getString(cursor.getColumnIndex(myDBHelper.QUANTITY));
                String price = cursor.getString(cursor.getColumnIndex(myDBHelper.PRICE));
                String image_url = cursor.getString(cursor.getColumnIndex(myDBHelper.IMAGE_URL));
                id = cursor.getInt(cursor.getColumnIndex(myDBHelper.ID));

                Product product = new Product(Integer.toString(id), name, quantity, price, "", image_url);

                myItemList.add(product);

                int priceQ = Integer.parseInt(price)*Integer.parseInt(quantity);
                //Log.i("123", price+ quantity+ priceQ);
                cartTotalPrice += priceQ;

//                myCartViewAdapter = new MyCartViewAdapter(myItemList);
//                cartViewRV.setAdapter(myCartViewAdapter);

            }

            while (cursor.moveToNext());

            beforeCoupon.setText("Total: $" + Integer.toString(cartTotalPrice));
            discount.setText("Discount(0%): $0");
            total.setText("Total: $" + Integer.toString(cartTotalPrice));

            butApply.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCoupon();

                }
            });

            butCheckout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();



                    String spTotal = total.getText().toString();
                    Log.i("eee", spTotal);
                    loginPrefsEditor.putString("totalwDiscount", spTotal);
                    loginPrefsEditor.commit();


                    FragmentManager fm = activity.getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.bottom_frame_layout, orderInfoFragment).addToBackStack(null).commit();
                }
            });
        }

        myCartViewAdapter = new MyCartViewAdapter(myItemList);
        cartViewRV.setAdapter(myCartViewAdapter);

        return view;
    }

    private void getCoupon() {
        String couponno = coupon.getText().toString();

        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/coupon.php?" +
                "api_key=" + api_key + "&user_id="+ user_id +"&couponno=" + couponno;

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Coupon discount");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String couponno = individual.getString("couponno");
                        String discount = individual.getString("discount");
                        Log.i("eee", discount);
                        discountInt = Integer.parseInt(discount);
                    }
                    beforeCoupon.setText("Total: $" + Integer.toString(cartTotalPrice));
                    discount.setText("Discount(" + discountInt + "%)" +": "+Integer.toString(cartTotalPrice*discountInt/100));
                    total.setText("Total: $" + Integer.toString(cartTotalPrice * (100 - discountInt) / 100));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}