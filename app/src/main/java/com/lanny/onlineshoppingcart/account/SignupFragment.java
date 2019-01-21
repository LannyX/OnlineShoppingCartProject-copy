package com.lanny.onlineshoppingcart.account;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.R;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements SignupContract.SignupView{

    public static final String TAG = SignupFragment.class
            .getSimpleName();
    EditText fname, lname, laddress, loginEmail, loginPassword, loginMobile;
    Button signupButton;
    private ProgressDialog pd;
    StringRequest stringRequest;
    boolean emailB, fnameB, passwordB, mobileB;
    SignupPresenter signupPresenter;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        fname = view.findViewById(R.id.editTextSFname);
        lname = view.findViewById(R.id.editTextSLname);
        laddress = view.findViewById(R.id.editTextSAddress);
        loginEmail = view.findViewById(R.id.editTextSEmail);
        loginPassword = view.findViewById(R.id.editTextSPassword);
        loginMobile = view.findViewById(R.id.editTextSMobile);
        signupButton = view.findViewById(R.id.buttonUpdateProfile);
        signupPresenter = new SignupPresenter(this);


//        emailB = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[\\w.]+@[\\w.]*\\.[a-zA-Z0-9]+$").matcher(loginEmail.getText().toString()).matches();
//
//        fnameB = Pattern.compile("\"(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$\"").matcher(fname.getText().toString()).matches();
//        LnameB = Pattern.compile("\"(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$\"").matcher(lname.getText().toString()).matches();
//        mobileB = Pattern.compile("(.)*(\\d)(.)*").matcher(loginMobile.getText().toString()).matches();
        
            signupButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailB = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(loginEmail.getText().toString()).matches();
                    mobileB = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,}$").matcher(loginMobile.getText().toString()).matches();
                    passwordB = Pattern.compile("^(?=.*[0-9])(?=\\S+$).{8,}$").matcher(loginPassword.getText().toString()).matches();
                    Log.i("aaa", emailB + " " + fnameB+ " "+ passwordB + "" +mobileB);

                    if(emailB != true){
                        Toast.makeText(getContext(), "Please use valid email address", Toast.LENGTH_SHORT).show();
                    }else if (mobileB != true){
                        Toast.makeText(getContext(), "Mobile phone must has at least 8 digits", Toast.LENGTH_SHORT).show();
                    }else if (passwordB != true){
                        Toast.makeText(getContext(), "Password must has at least 8 digits", Toast.LENGTH_SHORT).show();
                    }else {
                        String fname1 = fname.getText().toString();
                        String lname1 = lname.getText().toString();
                        String laddress1 = laddress.getText().toString();
                        String loginPass = loginPassword.getText().toString();
                        String loginE = loginEmail.getText().toString();
                        String loginM = loginMobile.getText().toString();
                        //SignUpRequest();
                        signupPresenter.SignUpRequest(fname1, lname1, laddress1, loginPass, loginE, loginM);
                    }
                }
            });



        return view;
    }

//    private void SignUpRequest() {
//        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php";
//        pd = new ProgressDialog(getContext());
//        pd.setMessage("Signing Up . . .");
//        pd.show();
//
//        stringRequest = new StringRequest(Method.POST, url,
//                new Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, response.toString());
//
//                        pd.hide();
//
//                    }
//                }, new ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                String message = null;
//                if (volleyError instanceof NetworkError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (volleyError instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
//                } else if (volleyError instanceof AuthFailureError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (volleyError instanceof ParseError) {
//                    message = "Parsing error! Please try again after some time!!";
//                } else if (volleyError instanceof NoConnectionError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (volleyError instanceof TimeoutError) {
//                    message = "Connection TimeOut! Please check your internet connection.";
//                }
//
//                Log.i(TAG, message.toString());
//            }
//        }){
//            @Override
//            public Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("fname", fname.getText().toString());
//                params.put("lname", lname.getText().toString());
//                params.put("address", laddress.getText().toString());
//                params.put("password", loginPassword.getText().toString());
//                params.put("email", loginEmail.getText().toString());
//                params.put("mobile", loginMobile.getText().toString());
//                return params;
//            }
//
//        };
//        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
//
//
//    }
    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
