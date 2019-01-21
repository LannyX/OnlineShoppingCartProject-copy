package com.lanny.onlineshoppingcart.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {


    public static final String TAG = SubCategoryFragment.class.getSimpleName();
    String res, scid;
    ArrayList<Product> myPL;
    MyProductListAdapter myPLAdapter;
    RecyclerView plRV;


    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        myPL = new ArrayList<>();
        plRV = view.findViewById(R.id.recyclerView3);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 1);
        plRV.setLayoutManager(gridLayoutManager3);
        plRV.setItemAnimator(new DefaultItemAnimator());

        Bundle b = this.getArguments();
        if (b != null){
            res = b.getString("id");
            scid = b.getString("scid");
            Log.i("xxx", res + scid);
        }

        getProductListDetails();


        return view;
    }

    private void getProductListDetails() {
        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/product_details.php?cid=" + res + "&scid=" + scid+ "&api_key=1fb4943265a962af385c70db975055fd&user_id=1472";

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("products");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String id = individual.getString("id");
                        String pname = individual.getString("pname");
                        String quantity = individual.getString("quantity");
                        String desc = individual.getString("discription");
                        String price = individual.getString("prize");
                        String image = individual.getString("image");

                        Product product = new Product(id, pname, quantity, price, desc, image);
//                        Log.i("xxx", categories.toString());

                        myPL.add(product);
                    }
                    myPLAdapter = new MyProductListAdapter(myPL);
                    plRV.setAdapter(myPLAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("xxx", error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }

}
