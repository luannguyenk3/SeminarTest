package com.nguyenphucduongluan.seminartest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nguyenphucduongluan.seminartest.databinding.ActivityAddBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean openCam;
    DataBases databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databases = new DataBases(this);

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
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đổi hình ảnh từ ImageView thành mảng byte
                byte[] imageBytes = imageViewToByteArray(binding.imvImage);

                // Tiến hành lưu dữ liệu vào cơ sở dữ liệu
                ContentValues values = new ContentValues();
                values.put(DataBases.COL_NAME, binding.edtName.getText().toString());
                values.put(DataBases.COL_PRICE, Double.parseDouble(binding.edtPrice.getText().toString()));
                values.put(DataBases.COL_DESCRIPTION, binding.edtDecript.getText().toString());
                values.put(DataBases.COL_PIC, imageBytes); // Đây là cột lưu trữ hình ảnh

                long numbrow = databases.getWritableDatabase().insert(DataBases.TBL_NAME, null, values);
                if (numbrow > 0) {
                    Toast.makeText(AddActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Phương thức để chuyển đổi ImageView thành mảng byte
    private byte[] imageViewToByteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
