package com.lanny.onlineshoppingcart;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.lanny.onlineshoppingcart.account.ProfileFragment;
import com.lanny.onlineshoppingcart.cart.CartMainFragment;
import com.lanny.onlineshoppingcart.order.OrderHistoryFragment;
import com.lanny.onlineshoppingcart.product.HomeFragment;
import com.lanny.onlineshoppingcart.wishlist.WishListFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar);

        navigationView = findViewById(R.id.BottomNavigationView);

        navigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom_frame_layout, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_account:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_cart:
                fragment = new CartMainFragment();
                break;
            case R.id.nav_history:
                fragment = new OrderHistoryFragment();
                break;
            case R.id.nav_wish:
                fragment = new WishListFragment();
                break;

        }


        return loadFragment(fragment);
    }
}
