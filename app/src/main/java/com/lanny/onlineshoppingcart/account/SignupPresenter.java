package com.lanny.onlineshoppingcart.account;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.account.SignupContract.SignupView;

import java.util.HashMap;
import java.util.Map;

public class SignupPresenter implements SignupContract.SignUp{

    public static final String TAG = SignupPresenter.class.getSimpleName();
//    StringRequest stringRequest;
    SignupContract.SignupView mView;
    AppController volley;
    String result;

    public SignupPresenter(SignupFragment context) {
        this.mView = (SignupView) context;
        volley =  AppController.getInstance();
    }

    @Override
    public void SignUpRequest(final String fname, final String lname, final String address, final String password,
                              final String email, final String mobile) {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php";

        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        mView.showToast(response);
                        result = response;

                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                Log.i(TAG, message.toString());
            }
        }){
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("address",address);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("password",password);
                return params;
            }

        };
        volley.addToRequestQueue(stringRequest, TAG);

    }
}
