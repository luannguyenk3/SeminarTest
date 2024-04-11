package com.nguyenphucduongluan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.MainActivity;
import com.nguyenphucduongluan.seminartest.R;

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
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_list, null);
            holder.txtName = convertView.findViewById(R.id.txtProductName);
            holder.txtPrice = convertView.findViewById(R.id.txtProductPrice);
            holder.txtScript = convertView.findViewById(R.id.txtProductDescription);
            holder.imvThumb = convertView.findViewById(R.id.imvImage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product p = products.get(position);
        holder.txtName.setText(p.getProductName());
        holder.txtScript.setText(p.getProductScript());
        holder.txtPrice.setText(String.valueOf(Math.round(p.getProductPrice())) + " đồng");
        holder.imvThumb.setImageBitmap(p.getProductThumb());

        return convertView;

    }
    public class ViewHolder {
        TextView txtName, txtPrice, txtScript;
        ImageView imvThumb;
    }
}
