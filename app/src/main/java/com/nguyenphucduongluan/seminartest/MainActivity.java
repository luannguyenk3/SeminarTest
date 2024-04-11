package com.nguyenphucduongluan.seminartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nguyenphucduongluan.Adapters.ProductAdapter;
import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static DataBases db = null;
    ProductAdapter adapter;
    ArrayList<Product> products;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDB();
    }

    private void createDB() {
        db = new DataBases(MainActivity.this);
    }

    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        products = new ArrayList<>();
        // Đọc từ db
        Cursor cursor = db.querryData("SELECT * FROM " + DataBases.TBL_NAME);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            double price = cursor.getDouble(3);
            byte[] imageBytes = cursor.getBlob(4); // Đọc dữ liệu từ cột BLOB

            // Tạo đối tượng Product từ dữ liệu đọc được
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            products.add(new Product(id, name, description, price, image));
        }
        cursor.close();

        adapter = new ProductAdapter(MainActivity.this, R.layout.item_list, products);
        binding.lvProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnAdd){
            // Mở AddActivity
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
