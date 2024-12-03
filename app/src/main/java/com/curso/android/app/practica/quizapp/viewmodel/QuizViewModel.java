package com.curso.android.app.practica.quizapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.curso.android.app.practica.quizapp.model.Question;
import com.curso.android.app.practica.quizapp.model.QuestionList;
import com.curso.android.app.practica.quizapp.repository.QuizRepository;

import java.util.List;

public class QuizViewModel extends ViewModel {

    private QuizRepository repository= new QuizRepository();

    private static LiveData<QuestionList> questionListLiveData;

    public QuizViewModel() {

        // Fetch the list of questions from the repository and store in LiveData
        questionListLiveData = repository.getQuestionsFromAPI();
    }

    // Getter method to access the question list LiveData
    public static LiveData<QuestionList> getQuestionList() {
        return questionListLiveData;
    }
}
