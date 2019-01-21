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
import android.widget.ImageView;
import android.widget.TextView;

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
public class SubCategoryFragment extends Fragment {

    public static final String TAG = SubCategoryFragment.class.getSimpleName();
    String result;
    TextView scName;
    ImageView scImage;
    ArrayList<ProductCategories> myCategories;
    MySubProductAdapter myAdapter;
    RecyclerView rV;

    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_sub_category, container, false);

        Bundle b = this.getArguments();
        if (b != null){
            result = b.getString("id");
            Log.i("xxx", result);
        }


        myCategories = new ArrayList<>();
        rV = v.findViewById(R.id.recyclerView2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rV.setLayoutManager(gridLayoutManager);
        rV.setItemAnimator(new DefaultItemAnimator());

        getSubCategoryDetails();


        return v;
    }

    private void getSubCategoryDetails() {
        //String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?Id="+ result +"&api_key=ce34165bb77cc992084f496189e0055e&user_id=1471";

        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=" + result + "&api_key=1fb4943265a962af385c70db975055fd&user_id=1472";
        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("subcategory");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String categoryId = individual.getString("scid");
                        String categoryName = individual.getString("scname");
                        String categoryImage = individual.getString("scimageurl");
                        String categoryDescription = individual.getString("scdiscription");

                        ProductCategories categories = new ProductCategories(result, categoryName, categoryImage, categoryDescription, categoryId);
//                        Log.i("xxx", categories.toString());

                        myCategories.add(categories);
                    }
                        myAdapter = new MySubProductAdapter(myCategories);
                        rV.setAdapter(myAdapter);

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
