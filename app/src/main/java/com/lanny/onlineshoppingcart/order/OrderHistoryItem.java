package com.lanny.onlineshoppingcart.order;

public class OrderHistoryItem {

    String orderid, orderstatus, name, billingadd, deliveryadd, mobile, email, itemid,
            itemname, itemquantity, totalprice, paidprice, placedon;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingadd() {
        return billingadd;
    }

    public void setBillingadd(String billingadd) {
        this.billingadd = billingadd;
    }

    public String getDeliveryadd() {
        return deliveryadd;
    }

    public void setDeliveryadd(String deliveryadd) {
        this.deliveryadd = deliveryadd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPaidprice() {
        return paidprice;
    }

    public void setPaidprice(String paidprice) {
        this.paidprice = paidprice;
    }

    public String getPlacedon() {
        return placedon;
    }

    public void setPlacedon(String placedon) {
        this.placedon = placedon;
    }

    public OrderHistoryItem(String orderid, String orderstatus, String name, String billingadd, String deliveryadd, String mobile, String email, String itemid, String itemname, String itemquantity, String totalprice, String paidprice, String placedon) {
        this.orderid = orderid;
        this.orderstatus = orderstatus;
        this.name = name;
        this.billingadd = billingadd;
        this.deliveryadd = deliveryadd;
        this.mobile = mobile;
        this.email = email;
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.totalprice = totalprice;
        this.paidprice = paidprice;
        this.placedon = placedon;


    }
}
