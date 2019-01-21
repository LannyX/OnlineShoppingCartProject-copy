package com.lanny.onlineshoppingcart.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lanny.onlineshoppingcart.R;

import java.util.List;

public class MyOrderHistoryAdapter extends RecyclerView.Adapter<MyOrderHistoryAdapter.MyViewHolder>{
    public View view;

    List<OrderHistoryItem> mOrder;


    public MyOrderHistoryAdapter(List<OrderHistoryItem> mOrder) {
        this.mOrder = mOrder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderidId, orderstatus, name, billingadd, deliveryadd, mobile, email, itemid
                , itemname, itemquantity, totalprice, paidprice, placedon;
        public Button shipmentTracking;
        public CardView bg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            orderidId = itemView.findViewById(R.id.textViewOrderId);
            orderstatus = itemView.findViewById(R.id.textViewOrderStatus);
            name = itemView.findViewById(R.id.textViewOrderName);
            billingadd = itemView.findViewById(R.id.textViewOrderBilling);
            name = itemView.findViewById(R.id.textViewOrderName);
            deliveryadd = itemView.findViewById(R.id.textViewOrderDelivery);
            mobile = itemView.findViewById(R.id.textViewOrderMobile);
            email = itemView.findViewById(R.id.textViewOrderEmail);
//            itemid = itemView.findViewById(R.id.textViewOrderItemid);
            itemname = itemView.findViewById(R.id.textViewOrderItemName);
//            itemquantity = itemView.findViewById(R.id.textViewOrderItemQuan);
            totalprice = itemView.findViewById(R.id.textViewOrderTotal);
            paidprice = itemView.findViewById(R.id.textViewOrderPaid);
            placedon = itemView.findViewById(R.id.textViewOrderPlacedon);
            shipmentTracking = itemView.findViewById(R.id.shipmentTracking);

            bg = itemView.findViewById(R.id.card_view);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_history_recycler_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        int order = getItemViewType(i);
        final OrderHistoryItem list = mOrder.get(order);
        //        final OrderHistoryItem list = mOrder.get(i);
        //myViewHolder.categoryId.setText((list.getCategoryId()));
        myViewHolder.orderidId.setText("Order ID: "+(list.getOrderid()));
        myViewHolder.orderstatus.setText("Order Status: " + (list.getOrderstatus()));
        myViewHolder.name.setText("First Name: " +(list.getName()));
        myViewHolder.billingadd.setText("Billing Address: "+ (list.getBillingadd()));
        myViewHolder.deliveryadd.setText("Delivery Address: " + (list.getDeliveryadd()));
        myViewHolder.mobile.setText("Mobile: "+ (list.getMobile()));
        myViewHolder.email.setText("Email: " + (list.getEmail()));
//        myViewHolder.itemid.setText("Item ID: " +(list.getItemid()));
        myViewHolder.itemname.setText("Item Name: " + (list.getItemname()));
//        myViewHolder.itemquantity.setText("Quantity: " + (list.getItemquantity()));
        myViewHolder.totalprice.setText("Total Price: $" + (list.getTotalprice()));
        myViewHolder.paidprice.setText("Paid Price: $" + (list.getPaidprice()));
        myViewHolder.placedon.setText("Order Placedon: " + (list.getPlacedon()));

        myViewHolder.shipmentTracking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShipmentTrackingFragment shipmentTrackingFragment = new ShipmentTrackingFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Bundle bundle = new Bundle();
                bundle.putString("order_id", list.getOrderid());
                shipmentTrackingFragment.setArguments(bundle);
                FragmentManager fm = activity.getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.bottom_frame_layout, shipmentTrackingFragment).addToBackStack(null).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mOrder.size()-1-position;
    }

}
