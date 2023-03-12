package com.vicentearmenta.androidtriviatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion1Binding;

public class Question1Activity extends AppCompatActivity {
    ActivityQuestion1Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* Esto aplica solo para la primer pregunta */
        binding.backButton.setVisibility(View.INVISIBLE);

        binding.nextButton.setEnabled(false);
    }
}