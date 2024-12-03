package com.curso.android.app.practica.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.curso.android.app.practica.quizapp.databinding.ActivityMainBinding;
import com.curso.android.app.practica.quizapp.model.Question;
import com.curso.android.app.practica.quizapp.model.QuestionList;
import com.curso.android.app.practica.quizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionList;

    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );

        // Resetting the scores
        result = 0;
        totalQuestions = 0;

        // Creating an instance of the QuizModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Displaying the first question
        displayFirstQuestion();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayNextQuestion();
            }
        });

    }


    public void displayFirstQuestion (){

        // Observing Livedate from a viewModel
        QuizViewModel.getQuestionList().observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(QuestionList questions) {
                questionList = questions;
                binding.txtQuestion.setText("Question 1: "+questions.get(0).getQuestion());
                binding.option1.setText(questions.get(0).getOption1());
                binding.option2.setText(questions.get(0).getOption2());
                binding.option3.setText(questions.get(0).getOption3());
                binding.option4.setText(questions.get(0).getOption4());

            }
        });
    }

    public void DisplayNextQuestion () {


        // Direct user to Results Activity
        if (binding.btnNext.getText().equals("Finish")){
            Intent intent = new Intent (MainActivity.this, ResultsActivity.class);
            startActivity(intent);
            finish();
        }

        // Displaying the question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1){
            RadioButton radioButton = findViewById(selectedOption);

            // More questions to display?
            if ((questionList.size()-i) > 0 ) {
                // get the number of questions
                totalQuestions = questionList.size();

                // check if the chosen option is correct
                if (radioButton.getText().toString().equals(
                        questionList.get(i).getCorrectOption()
                )){
                    result++;
                    // String answerString = "correct answer: "+ result;
                    binding.textResult.setText("correct answer: "+ result);
                }
                if (i==0){
                    i++;
                }

                // Display next Question
                // String txtQuestionString = "Question "+(i+1)+ ": "+ questionList.get(i).getQuestion();
                binding.txtQuestion.setText("Question "+(i+1)+ ": "+ questionList.get(i).getQuestion());
                binding.option1.setText(questionList.get(i).getOption1());
                binding.option2.setText(questionList.get(i).getOption2());
                binding.option3.setText(questionList.get(i).getOption3());
                binding.option4.setText(questionList.get(i).getOption4());

                //check if its the last question
                if (i == questionList.size()-1){
                    binding.btnNext.setText("Finish");
                }

                binding.radioGroup.clearCheck();
                i++;
            } else {
                if (radioButton.getText().toString().equals(
                        questionList.get(i-1).getCorrectOption()
                )){
                    result++;
                    // String correctAnswer = "Correct Answer: "+ result;
                    binding.textResult.setText("Correct Answer: "+ result);
                }
            }

        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }
}