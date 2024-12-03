package com.curso.android.app.practica.quizapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.curso.android.app.practica.quizapp.api.QuizApiService;
import com.curso.android.app.practica.quizapp.api.RetrofitClient;
import com.curso.android.app.practica.quizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {

    private QuizApiService questionsAPI;

    public QuizRepository() {
        // Create an instance of QuestionsAPI using Retrofit
        this.questionsAPI = RetrofitClient.getRetrofitInstance().create(QuizApiService.class);

    }

    public LiveData<QuestionList> getQuestionsFromAPI(){
        MutableLiveData<QuestionList> data = new MutableLiveData<>();

        Call<QuestionList> response = questionsAPI.getQuestions();

        // in the following line the enqueue method is executed on a background thread
        // (if we used .execute method instead of .enqueue, it would be executed on the main thread)

        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                QuestionList list = response.body();
                data.setValue(list);
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable throwable) {

            }
        });

        return data;
    }


}
