package com.lanny.onlineshoppingcart.order;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lanny.onlineshoppingcart.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInfoFragment extends Fragment {

    EditText address, billingad;
    public SharedPreferences loginPreferences;
    public SharedPreferences.Editor loginPrefsEditor;
    Card card;
    Stripe stripe;
    Button butPay;

    public OrderInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order_info, container, false);

        address = view.findViewById(R.id.editTextAddress);
        billingad = view.findViewById(R.id.editTextBillingAddress);
        butPay = view.findViewById(R.id.buttonPay);

        loginPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        String totalwDiscount = loginPreferences.getString("totalwDiscount", "");

        final CardMultilineWidget mCardInputWidget = (CardMultilineWidget) view.findViewById(R.id.card_multiline_widget);
        stripe = new Stripe(getContext(), "pk_test_cEc9fRoy1bIoOU6QLtpybIhr");


        butPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPrefsEditor.putString("spAddress", address.getText().toString());
                loginPrefsEditor.putString("spBillingAddress", billingad.getText().toString());
                loginPrefsEditor.commit();

                card = mCardInputWidget.getCard();
                //card = new Card("5105105105105100", 3, 2021, "789");
                if (card != null){
                    stripe.createToken(
                            card,
                            new TokenCallback() {
                                public void onSuccess(Token token) {
                                    // Send token to your server
                                    OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                                    FragmentManager fm = activity.getSupportFragmentManager();
                                    fm.beginTransaction()
                                            .replace(R.id.bottom_frame_layout, orderDetailsFragment).addToBackStack(null).commit();

                                }
                                public void onError(Exception error) {
                                    Toast.makeText(getContext(), "Card Information is not corret", Toast.LENGTH_SHORT).show();

                                }
                            }
                    );
                }else{
                    Toast.makeText(getContext(), "Need to enter card", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

}
