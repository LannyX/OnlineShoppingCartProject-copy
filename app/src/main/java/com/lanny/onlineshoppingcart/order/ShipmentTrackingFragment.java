package com.lanny.onlineshoppingcart.order;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentTrackingFragment extends Fragment {
    public static final String TAG = ShipmentTrackingFragment.class.getSimpleName();

    String orderId, api_key, user_id;
    private SharedPreferences loginPreferences;
    TextView shipmentid, shipmentstatus, shiptmentnote;

    public ShipmentTrackingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_tracking, container, false);


        shipmentid = view.findViewById(R.id.textViewShipmentID);
        shipmentstatus = view.findViewById(R.id.textViewShipmentStatus);
        shiptmentnote = view.findViewById(R.id.textViewShipmentNote);

        shiptmentnote.setText("Note:\n" +
                " \n" +
                "If Received results as \" shipmentstatus \":\"1\"\nIts means Order Confirm\n" +
                "If Received results as \" shipmentstatus \":\"2\"\nIts means Order Dispatch\n" +
                "If Received results as \" shipmentstatus \":\"3\"\nIts means Order On the Way\n" +
                "If Received results as \" shipmentstatus \":\"4\"\nIts means Order Delivered\n");

        Bundle b = this.getArguments();
        if (b != null){
            orderId = b.getString("order_id");
        }

        loginPreferences = this.getActivity().getSharedPreferences("profile", MODE_PRIVATE);
        user_id = loginPreferences.getString("spId", "");
        api_key = loginPreferences.getString("spApiKeys", "");

        getShipmentTrack();

        return view;
    }

    private void getShipmentTrack() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shipment_track.php?" +
                "api_key="+ api_key +"&user_id=" +user_id +"&order_id=" +orderId;
//        Log.i(TAG, url.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Shipment track");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String id = individual.getString("shipmentid");
                        String status = individual.getString("shipmentstatus");

                        shipmentid.setText("Shipmentid: " +id);
                        shipmentstatus.setText("Shipment Status: " + status);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }

}
