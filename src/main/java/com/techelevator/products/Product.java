package com.techelevator.products;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private String name;
    private BigDecimal price;
    private int productCount;

    private String productType;
    private static final int  MAXIMUM_QUANTITY_AVAILABLE = 5;

    public Product() {}

    public Product(String name, BigDecimal price, String productType, int productCount) {
        this.name = name;
        price = price.setScale(2, RoundingMode.CEILING);
        this.price = price;
        this.productType = productType;
        this.productCount = productCount;
    }

    public Product (String[] productDetails) {

        final int PRODUCT_NAME = 1;
        final int PRICE = 2;
        final int PRODUCT_TYPE = 3;

        this.name = productDetails[PRODUCT_NAME];
        this.price = new BigDecimal(productDetails[PRICE]);
        this.productType = productDetails[PRODUCT_TYPE];
        this.productCount = MAXIMUM_QUANTITY_AVAILABLE;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void decrementProductCount(){
        this.productCount--;
    }
}
