package com.example.jettrivia.repository

import com.example.jettrivia.model.Questions
import com.example.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    suspend fun getAllQuestions(): Questions = api.getAllQuestions()

}