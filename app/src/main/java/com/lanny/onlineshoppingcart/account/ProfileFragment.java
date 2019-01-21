package com.lanny.onlineshoppingcart.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private EditText fname, lname, laddress, loginEmail, loginMobile;
    private Button updateButton, resetPassword;
    private ProgressDialog pd;
    private StringRequest stringRequest;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        fname = view.findViewById(R.id.editTextUpdateFname);
        lname = view.findViewById(R.id.editTextUpdateLName);
        laddress = view.findViewById(R.id.editTextUpdateAddress);
        loginEmail = view.findViewById(R.id.editTextUpdateEmail);
        loginMobile = view.findViewById(R.id.editTextUpdateMobile);
        updateButton = view.findViewById(R.id.buttonUpdateProfile);
        resetPassword = view.findViewById(R.id.buttonResetPassword);
        loginPreferences = getActivity().getSharedPreferences("profile", MODE_PRIVATE);

        fname.setText(loginPreferences.getString("spFName", ""));
        lname.setText(loginPreferences.getString("spLName", ""));
        loginEmail.setText(loginPreferences.getString("spEmail", ""));
        loginMobile.setText(loginPreferences.getString("spMobile", ""));

        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRequest();
            }
        });

        resetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        return view;

    }

    private void UpdateRequest() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?";
        pd = new ProgressDialog(getContext());
        pd.setMessage("Updating . . .");
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
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("address", laddress.getText().toString());
                params.put("email", loginEmail.getText().toString());
                params.put("mobile", loginMobile.getText().toString());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }


    public void resetPassword() {

        Intent i = new Intent(getActivity(), ResetPasswordActivity.class);
        ProfileFragment.this.startActivity(i);

    }
}
