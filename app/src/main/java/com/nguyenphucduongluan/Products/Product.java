package com.nguyenphucduongluan.Products;

public class Product {
String bookName, bookScript;
double bookPrice;
int bookThumb;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookScript() {
        return bookScript;
    }

    public void setBookScript(String bookScript) {
        this.bookScript = bookScript;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getBookThumb() {
        return bookThumb;
    }

    public void setBookThumb(int bookThumb) {
        this.bookThumb = bookThumb;
    }

    public Product(String bookName, String bookScript, double bookPrice, int bookThumb) {
        this.bookName = bookName;
        this.bookScript = bookScript;
        this.bookPrice = bookPrice;
        this.bookThumb = bookThumb;
    }
}
