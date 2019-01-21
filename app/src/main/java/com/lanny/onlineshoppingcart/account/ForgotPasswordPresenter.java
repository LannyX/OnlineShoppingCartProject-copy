package com.lanny.onlineshoppingcart.account;

import android.app.ProgressDialog;
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
import com.lanny.onlineshoppingcart.account.ForgotPasswordContract.ForgotView;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordPresenter implements ForgotPasswordContract.ForgotPresenter{
    private ForgotPasswordContract.ForgotView mView;
    private AppController volley;
    static private final String TAG = ForgotPasswordPresenter.class.getSimpleName();
    private ProgressDialog pd;

    public ForgotPasswordPresenter(ForgotPasswordActivity activity) {
        this.mView = (ForgotView) activity;
        volley = AppController.getInstance();
    }

    @Override
    public void forgotPassword(final String email) {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/forgot_pass_email.php?";


        StringRequest stringRequest = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        pd.hide();

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
                params.put("email", email);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}
