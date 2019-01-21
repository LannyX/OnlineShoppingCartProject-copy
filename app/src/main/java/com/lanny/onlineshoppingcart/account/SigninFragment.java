package com.lanny.onlineshoppingcart.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;

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
import com.lanny.onlineshoppingcart.MainActivity;
import com.lanny.onlineshoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {
    public static final String TAG = SigninFragment.class
            .getSimpleName();
    private EditText loginPassword, loginMobile;
    private Button loginButton;
    private ProgressDialog pd;
    private TextView forgortPassword;
    StringRequest stringRequest;
    public SharedPreferences loginPreferences;
    public SharedPreferences.Editor loginPrefsEditor;
    Boolean worked = false;
    String mobile, password;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);

        loginPassword = view.findViewById(R.id.editTextLoginPassword);
        loginMobile = view.findViewById(R.id.editTextLoginMobile);
        loginButton = view.findViewById(R.id.button_log_in);
        forgortPassword = view.findViewById(R.id.textView_Forgot_password);


        loginPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                loginRequest();


                if(worked){
//                    moveToNewActivity();
//                    loginPrefsEditor.putString("spMobile", loginMobile.getText().toString());
//                    loginPrefsEditor.putString("spPassword", loginPassword.getText().toString());
//                    loginPrefsEditor.commit();

                    loginPassword.setText(loginPreferences.getString("spPassword", ""));
                    loginMobile.setText(loginPreferences.getString("spMobile", ""));
                }


            }
        });


        forgortPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToForgotPasswordActivity();
            }
        });

        return view;
    }

    private void moveToForgotPasswordActivity() {
        Intent i = new Intent(getActivity(), ForgotPasswordActivity.class);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(0,0);

    }


    private void loginRequest() {
        mobile = loginMobile.getText().toString();
        password = loginPassword.getText().toString();
//        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?";
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?" +
                "mobile="+ mobile +"&password="+ password;
        pd = new ProgressDialog(getContext());
        pd.setMessage("Signing In . . .");
        pd.show();

        stringRequest = new StringRequest(Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        worked = true;

                        try {
                            JSONArray jasonArray = new JSONArray(response);

                            for (int i = 0; i < jasonArray.length(); i++) {
                                JSONObject individual = jasonArray.getJSONObject(i);
                                String id = individual.getString("id");
                                String fname = individual.getString("firstname");
                                String lname = individual.getString("lastname");
                                String email = individual.getString("email");
                                String mobile = individual.getString("mobile");
                                String apiKeys = individual.getString("appapikey ");
                                Log.i("xxx", id + "\n"+ apiKeys);

                                loginPrefsEditor.putString("spId", id);
                                loginPrefsEditor.putString("spFName", fname);
                                loginPrefsEditor.putString("spLName", lname);
                                loginPrefsEditor.putString("spEmail", email);
                                loginPrefsEditor.putString("spMobile", mobile);
                                loginPrefsEditor.putString("spApiKeys", apiKeys);
                                loginPrefsEditor.commit();

                            }
                            moveToNewActivity();
                            loginPrefsEditor.putString("spMobile", loginMobile.getText().toString());
                            loginPrefsEditor.putString("spPassword", loginPassword.getText().toString());
                            loginPrefsEditor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "no response");
                        }

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

                Log.i("xxx", message.toString());
            }
        });
//        {
//            @Override
//            public Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobile", loginMobile.getText().toString());
//                params.put("password", loginPassword.getText().toString());
//                return params;
//
//            }

//        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }


    private void moveToNewActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0,0);
    }

}
