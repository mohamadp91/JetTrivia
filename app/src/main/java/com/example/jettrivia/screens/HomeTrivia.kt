package com.example.jettrivia.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettrivia.components.ErrorDialog
import com.example.jettrivia.components.Question
import com.example.jettrivia.data.Result
import com.example.jettrivia.model.Questions

@Composable
fun HomeTrivia(viewModel: QuestionViewModel = hiltViewModel()) {

    var result = viewModel.questionResult.collectAsState()

    when (result.value) {
        is Result.Success<*> -> {
            val questions = ((result.value as Result.Success<*>).data as Questions)
            Question(
                questions = questions
            )
        }
        is Result.Error -> {
            val message = (result.value as Result.Error).exception.message.toString()
            ErrorDialog(message = message,
                onClick = { viewModel.getAllQuestions() })
        }
        is Result.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}