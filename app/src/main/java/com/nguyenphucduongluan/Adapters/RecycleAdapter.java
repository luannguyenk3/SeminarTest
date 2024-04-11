package com.nguyenphucduongluan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.R;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    Context context;
    List<Product> products;

    public RecycleAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(products.get(position).getProductName());
        holder.txtPrice.setText(String.valueOf(Math.round(products.get(position).getProductPrice())));
        holder.txtDescription.setText(products.get(position).getProductScript());
        holder.imvThumb.setImageBitmap(products.get(position).getProductThumb());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtDescription;
        ImageView imvThumb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtProductPrice);
            txtDescription = itemView.findViewById(R.id.txtProductDescription);
            imvThumb = itemView.findViewById(R.id.imvImage);
        }
    }
}
