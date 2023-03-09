package com.example.jettrivia.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.Questions
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) : ViewModel() {

    var result: MutableState<DataOrException<Questions, Boolean, Exception>> =
        mutableStateOf(DataOrException())

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            try {
                result.value.loading = true
                result.value.data = questionRepository.getAllQuestions()
            } catch (e: Exception) {
                Log.e("View Model", e.localizedMessage)
                result.value.exception = e
            } finally {
                result.value.loading = false
            }
        }
    }
}