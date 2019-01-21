package com.lanny.onlineshoppingcart.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.lanny.onlineshoppingcart.R;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.ForgotView{
    public static final String TAG = ProfileFragment.class.getSimpleName();
    private ForgotPasswordContract.ForgotPresenter forgotPresenter;
    private EditText email;
    private Button but;
    private ProgressDialog pd;
    private StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.editTextForgotEmail);

    }

    public void forgotPasswordHandler(View view) {
//        forgotPassword();
        if(email.getText()!=null) {
            String emailS = email.getText().toString();
            forgotPresenter.forgotPassword(emailS);
        }
        else {
            this.showToast("Please enter an email!");
        }
    }

//    private void forgotPassword() {
//        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/forgot_pass_email.php?";
//        pd = new ProgressDialog(this);
//        pd.setMessage("Requesting . . .");
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
//                params.put("email", email.getText().toString());
//
//                return params;
//            }
//
//        };
//        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
//    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
