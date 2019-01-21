package com.lanny.onlineshoppingcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lanny.onlineshoppingcart.account.AccountFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AccountFragment accountFragment = new AccountFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sign_up_activity, accountFragment).commit();
    }
}
