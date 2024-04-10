package com.nguyenphucduongluan.seminartest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nguyenphucduongluan.seminartest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}