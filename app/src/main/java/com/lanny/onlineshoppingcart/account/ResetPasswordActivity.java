package com.lanny.onlineshoppingcart.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.lanny.onlineshoppingcart.R;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private EditText mobile, oldPassword, newPassword;
    private Button resetButton;
    private ProgressDialog pd;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mobile = findViewById(R.id.editTextResetMobile);
        oldPassword = findViewById(R.id.editTextResetOldP);
        newPassword = findViewById(R.id.editTextResetNewP);

    }

    public void resetPasswordButtonHandler(View view) {
        resetPassword();
    }

    private void resetPassword() {

        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?";
        pd = new ProgressDialog(this);
        pd.setMessage("Reseting . . .");
        pd.show();

        stringRequest = new StringRequest(Method.POST, url,
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
                params.put("mobile", mobile.getText().toString());
                params.put("password", oldPassword.getText().toString());
                params.put("newpassword", newPassword.getText().toString());

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}
