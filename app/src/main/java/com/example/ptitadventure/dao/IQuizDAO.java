package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.Quiz;

public interface IQuizDAO {
    void addQuiz(Quiz quiz);
    Quiz getQuizById(String quizId);
    void updateQuiz(Quiz quiz);
}
