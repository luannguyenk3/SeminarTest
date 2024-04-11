package com.nguyenphucduongluan.seminartest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nguyenphucduongluan.Products.Product;
import com.nguyenphucduongluan.seminartest.databinding.ActivityEditBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    Product p;
    DataBases databases;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean openCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
        addEvents();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (openCam) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    binding.imvImage.setImageBitmap(photo);
                } else {
                    Uri selectedPhoto = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPhoto);
                        binding.imvImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        databases = new DataBases(this);
    }

    private void getData() {
        Bitmap bitmap = null;
        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            bitmap = BitmapFactory.decodeFile(imagePath);
            // Sử dụng bitmap ở đây
        } else {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }



        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String description = getIntent().getStringExtra("description");
        int code = getIntent().getIntExtra("code", -1);

        binding.edtName.setText(name);
        binding.edtPrice.setText(String.valueOf(price));
        binding.edtDecript.setText(description);
        binding.imvImage.setImageBitmap(bitmap);
        p = new Product(code, name, description, price, bitmap);
    }


    private void addEvents() {
        binding.imvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(intent);
            }
        });
        binding.imvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateData() {
        byte[] imageBytes = imageViewToByteArray(binding.imvImage);
        ContentValues values = new ContentValues();
        values.put(DataBases.COL_NAME, binding.edtName.getText().toString());
        values.put(DataBases.COL_PRICE, Double.parseDouble(binding.edtPrice.getText().toString()));
        values.put(DataBases.COL_DESCRIPTION, binding.edtDecript.getText().toString());
        values.put(DataBases.COL_PIC, imageBytes);

        if (p != null) {
            int rowsAffected = databases.getWritableDatabase().update(DataBases.TBL_NAME, values, DataBases.COL_CODE + " = ?", new String[]{String.valueOf(p.getProductCode())});
            if (rowsAffected > 0) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            Toast.makeText(this, "No product data to update", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] imageViewToByteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
