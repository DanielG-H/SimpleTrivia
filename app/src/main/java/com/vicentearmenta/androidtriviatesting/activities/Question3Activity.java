package com.vicentearmenta.androidtriviatesting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion3Binding;

public class Question3Activity extends Question1Activity {

    ActivityQuestion3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setEnabled(false);
    }
}