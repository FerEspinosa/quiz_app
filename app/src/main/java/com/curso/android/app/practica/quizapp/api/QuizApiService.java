package com.curso.android.app.practica.quizapp.api;


import com.curso.android.app.practica.quizapp.model.QuestionList;


import retrofit2.Call;
import retrofit2.http.GET;


public interface QuizApiService {

    // Define the endpoint for fetching questions
    @GET("my_quiz_api.php")
    Call<QuestionList> getQuestions();

}
