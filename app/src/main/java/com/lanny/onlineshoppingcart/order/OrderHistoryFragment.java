package com.lanny.onlineshoppingcart.order;


import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {
    public static final String TAG = OrderHistoryFragment.class.getSimpleName();
    ArrayList<OrderHistoryItem> myList;
    RecyclerView recyclerView;
    MyOrderHistoryAdapter myAdapter;
    private SharedPreferences loginPreferences;
    String api_key, user_id, user_mobile;


    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_history, container, false);

        myList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewOrderHistory);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loginPreferences = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);
        user_id = loginPreferences.getString("spId", "");
        user_mobile = loginPreferences.getString("spMobile", "");
        api_key = loginPreferences.getString("spApiKeys", "");


        getOrderHistory();
        
        return view;
    }

    private void getOrderHistory() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/order_history.php?" +
                "api_key=" + api_key +"&user_id="+ user_id +"&mobile=" + user_mobile;

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Order history");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String orderidId = individual.getString("orderid");
                        String orderstatus = individual.getString("orderstatus");
                        String name = individual.getString("name");
                        String billingadd = individual.getString("billingadd");
                        String deliveryadd = individual.getString("deliveryadd");
                        String mobile = individual.getString("mobile");
                        String email = individual.getString("email");
                        String itemid = individual.getString("itemid");
                        String itemname = individual.getString("itemname");
                        String itemquantity = individual.getString("itemquantity");
                        String totalprice = individual.getString("totalprice");
                        String paidprice = individual.getString("paidprice");
                        String placedon = individual.getString("placedon");

                        OrderHistoryItem orderHistoryItem = new OrderHistoryItem(orderidId, orderstatus,
                                name, billingadd, deliveryadd, mobile, email, itemid, itemname, itemquantity,
                                totalprice, paidprice, placedon);

                        myList.add(orderHistoryItem);
//                        Log.i("xxx", categories.toString());

                    }
                    myAdapter = new MyOrderHistoryAdapter(myList);
                    recyclerView.setAdapter(myAdapter);

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
