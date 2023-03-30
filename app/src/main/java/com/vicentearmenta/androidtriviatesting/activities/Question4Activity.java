package com.vicentearmenta.androidtriviatesting.activities;

import android.os.Bundle;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion4Binding;

public class Question4Activity extends Question1Activity {

    ActivityQuestion4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setEnabled(false);
    }
}