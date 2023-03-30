package com.vicentearmenta.androidtriviatesting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion5Binding;

public class Question5Activity extends Question1Activity {

    ActivityQuestion5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setEnabled(false);
    }
}