package com.nguyenphucduongluan.Products;

import android.graphics.Bitmap;

public class Product { int productCode;
String productName;
String productScript;
double productPrice;
Bitmap productThumb;

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductScript() {
        return productScript;
    }

    public void setProductScript(String productScript) {
        this.productScript = productScript;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Bitmap getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(Bitmap productThumb) {
        this.productThumb = productThumb;
    }

    public Product(int productCode, String productName, String productScript, double productPrice, Bitmap productThumb) {
        this.productCode = productCode;
        this.productName = productName;
        this.productScript = productScript;
        this.productPrice = productPrice;
        this.productThumb = productThumb;
    }
}

