package com.vicentearmenta.androidtriviatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion2Binding;

public class Question2Activity extends AppCompatActivity {

    ActivityQuestion2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setEnabled(false);
    }
}