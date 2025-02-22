package com.exm.ordermanagement.dto;

public class ProductRequestDTO {

    private String name;
    private double price;
    private int stockQuantity;
    private int categoryId;
    private String image;

    public ProductRequestDTO(String name, double price, int stockQuantity, int category_id, String image) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = category_id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
