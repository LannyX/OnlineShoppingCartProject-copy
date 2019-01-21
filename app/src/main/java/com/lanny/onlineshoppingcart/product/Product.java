package com.lanny.onlineshoppingcart.product;

public class Product {

    String id, pname, quantity, price, desc, image;

    public Product(String id, String pname, String quantity, String price, String desc, String image) {
        this.id = id;
        this.pname = pname;
        this.quantity = quantity;
        this.price = price;
        this.desc = desc;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

//    public String getQuantity() {
//        return quantity;
//    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
