package com.vicentearmenta.androidtriviatesting.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.vicentearmenta.androidtriviatesting.R;
import com.vicentearmenta.androidtriviatesting.database.DatabaseOperations;
import com.vicentearmenta.androidtriviatesting.databinding.ActivityQuestion1Binding;
import com.vicentearmenta.androidtriviatesting.models.Answer;
import com.vicentearmenta.androidtriviatesting.models.Question;

import java.util.List;

public class Question1Activity extends AppCompatActivity {
    ActivityQuestion1Binding binding;
    DatabaseOperations mDBOperations;

    String userId;
    String questionsAlreadyAsked;

    int finalCorrectAnswerRdBtn = 0;
    int questionScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userId = intent.getStringExtra("USERID");
        questionsAlreadyAsked = intent.getStringExtra("QUESTIONS");

        mDBOperations = new DatabaseOperations(Question1Activity.this);

        /* Esto aplica solo para la primer pregunta */
        binding.backButton.setVisibility(View.INVISIBLE);

        binding.nextButton.setEnabled(false);

        Question question = mDBOperations.getNextQuestion(questionsAlreadyAsked);

        binding.questionText.setText(question.getQuestionText());

        String drawableName = "image" + question.getQuestionId();
        binding.imagePlaceholder.setImageResource(getResources().getIdentifier(drawableName, "drawable", getPackageName()));

        questionsAlreadyAsked = questionsAlreadyAsked + "," + question.getQuestionId();

        List<Answer> answers = question.getAllAnswers();
        for (int i = 0; i < 4; i++) {
            RadioButton tempRadioButton = (RadioButton) binding.rgAnswers.getChildAt(i);
            Answer tempAnswer = answers.get(i);

            if (question.getQuestionAnswer().equals(tempAnswer.getAnswerId())) {
                finalCorrectAnswerRdBtn = tempRadioButton.getId();
            }

            tempRadioButton.setText(tempAnswer.getAnswerText());
        }

        binding.rgAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                binding.nextButton.setEnabled(true);
                // vamos a revisar que eligio el user
                questionScore = evaluateAnswerSelection(radioGroup, i);
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Question1Activity.this, Question2Activity.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("QUESTIONS", "0");
            }
        });
    }

    public int evaluateAnswerSelection(RadioGroup radioGroup, int selectedAnswer) {
        int score = 0;

        RadioButton tempRdButton = findViewById(finalCorrectAnswerRdBtn);
        //tempRdButton.setButtonDrawable(R.drawable.ic_correct); // ic_check usar en icons
        tempRdButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#9AD680")));

        if (selectedAnswer == finalCorrectAnswerRdBtn) {
            score++;
        } else {
            RadioButton tempRdButton2 = findViewById(selectedAnswer);
            //tempRdButton2.setButtonDrawable(R.drawable.ic_wrong);
            tempRdButton2.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
        }

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setClickable(false);
        }

        return score;
    }
}