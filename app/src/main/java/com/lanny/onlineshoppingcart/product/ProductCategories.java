package com.lanny.onlineshoppingcart.product;

public class ProductCategories {

    String categoryId, scid, categoryName, categoryImage, categoryDescription;

    public ProductCategories(String categoryId, String categoryName, String categoryImage, String categoryDescription, String scid) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryDescription = categoryDescription;
        this.scid = scid;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getScid() {
        return scid;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
