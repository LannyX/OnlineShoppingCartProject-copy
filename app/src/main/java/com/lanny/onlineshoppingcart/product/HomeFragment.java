package com.lanny.onlineshoppingcart.product;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.lanny.onlineshoppingcart.AppController;
import com.lanny.onlineshoppingcart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    RecyclerView recyclerView;
    MyProductAdapter myAdapter;
    ArrayList<ProductCategories> myCategories;
    private SharedPreferences loginPreferences;
    String id, api_key;
    private SliderLayout mDemoSlider;
    ArrayList<String> listUrl = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myCategories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
//        loginPreferences = getSharedPreferences("profile", MODE_PRIVATE);
//
//        api_key = loginPreferences.getString("spApiKeys", "");
//        id = loginPreferences.getString("spId", "");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getCategories();
        getTopSeller();


        return view;
    }



    private void getCategories() {
        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key=1fb4943265a962af385c70db975055fd&user_id=1472";

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String categoryId = individual.getString("cid");
                        String categoryName = individual.getString("cname");
                        String categoryImage = individual.getString("cimagerl");
                        String categoryDescription = individual.getString("cdiscription");

                        ProductCategories categories = new ProductCategories(categoryId, categoryName, categoryImage, categoryDescription, null);
//                        Log.i("xxx", categories.toString());
                        myCategories.add(categories);
                    }
                    myAdapter = new MyProductAdapter(myCategories);
                    recyclerView.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);

    }


    private void getTopSeller() {
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_top_sellers.php?";

        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Top sellers");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject individual = jsonArray.getJSONObject(i);
                        String sellerid = individual.getString("id");
                        String sellername = individual.getString("sellername");
                        String sellerdeal = individual.getString("sellerdeal");
                        String sellerrating = individual.getString("sellerrating");
                        String sellerlogo = individual.getString("sellerlogo");

                        listUrl.add(sellerlogo);
                        listName.add("Name: "+sellername + "  Deal:  " + sellerdeal + "     Ratings:  " + sellerrating);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "no response");
                }

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.fitCenter();

                for (int i = 0; i < listUrl.size(); i++) {
                    TextSliderView sliderView = new TextSliderView(getContext());
                    // if you want show image only / without description text use DefaultSliderView instead

                    // initialize SliderLayout
                    sliderView
                            .image(listUrl.get(i))
                            .description(listName.get(i))
                            .setRequestOption(requestOptions)
                            .setBackgroundColor(Color.WHITE)
                            .setProgressBarVisible(true);

                    //add your extra information
//                    sliderView.bundle(new Bundle());
//                    sliderView.getBundle().putString("extra", listName.get(i));
                    mDemoSlider.addSlider(sliderView);
                }

                // set Slider Transition Animation
                // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider.setDuration(4000);
//                mDemoSlider.addOnPageChangeListener(this);

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, TAG);


    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
