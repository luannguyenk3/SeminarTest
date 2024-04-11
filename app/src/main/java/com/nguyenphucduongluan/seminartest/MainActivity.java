package com.nguyenphucduongluan.seminartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
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
import com.nguyenphucduongluan.Adapters.RecycleAdapter;
import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static DataBases db = null;
    RecycleAdapter adapter;
    ArrayList<Product> products;



    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                //Toast.makeText(MainActivity2.this, "To Left", Toast.LENGTH_SHORT).show();
                Product p = products.get(viewHolder.getAdapterPosition());
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                if(p != null) {
                    String imagePath = saveImageToFile(p.getProductThumb());
                    intent.putExtra("imagePath", imagePath);
                    intent.putExtra("name", p.getProductName());
                    intent.putExtra("price", p.getProductPrice());
                    intent.putExtra("description", p.getProductScript());
                    intent.putExtra("code", p.getProductCode());

                }
                startActivity(intent);
            } else if (direction == ItemTouchHelper.RIGHT) {
                Product p = products.get(viewHolder.getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận xóa?");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm " + p.getProductName() + "?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean delete = false;
                        if (p != null) {
                            delete = db.execSql("DELETE FROM " + DataBases.TBL_NAME + " WHERE " + DataBases.COL_CODE + " = " + p.getProductCode());
                        }
                        if(delete){
                            Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
    };

    private String saveImageToFile(Bitmap bitmap) {
        File filesDir = getFilesDir();
        File imageFile = new File(filesDir, "image.jpg");

        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    ItemTouchHelper itemTouchHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.lvProduct);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.lvProduct.setLayoutManager(linearLayoutManager);
        products = new ArrayList<>();
        // Đọc từ db
        Cursor cursor = db.querryData("SELECT * FROM " + DataBases.TBL_NAME);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            double price = cursor.getDouble(3);
            byte[] imageBytes = cursor.getBlob(4); // Đọc dữ liệu từ cột BLOB

            // Kiểm tra xem dữ liệu từ cột BLOB có null không trước khi tạo đối tượng Bitmap
            Bitmap image = null;
            if (imageBytes != null) {
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }

            products.add(new Product(id, name, description, price, image));
        }

        adapter = new RecycleAdapter(MainActivity.this, products);
        binding.lvProduct.setAdapter(adapter);
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
