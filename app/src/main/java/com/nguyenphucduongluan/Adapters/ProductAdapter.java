package com.nguyenphucduongluan.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter  extends BaseAdapter {
    MainActivity context;
    int item_list;
    List<Product> products;

    public ProductAdapter(MainActivity context, int item_list, List<Product> products) {
        this.context = context;
        this.item_list = item_list;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    public class ViewHolder {
        TextView txtName, txtPrice, txtScript;
        ImageView imvThumb;
    }
}
