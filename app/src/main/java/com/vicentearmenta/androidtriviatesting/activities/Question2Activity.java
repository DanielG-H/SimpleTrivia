package com.vicentearmenta.androidtriviatesting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion2Binding;

public class Question2Activity extends Question1Activity {

    ActivityQuestion2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setEnabled(false);
    }
}