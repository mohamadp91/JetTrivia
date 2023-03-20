package com.example.jettrivia.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.Result
import com.example.jettrivia.model.Questions
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {

    private var _questionResult: MutableStateFlow<Result<Questions>> =
        MutableStateFlow<Result<Questions>>(Result.Loading)

    val questionResult get() = _questionResult.asStateFlow()

    init {
        getAllQuestions()
    }

    fun getAllQuestions() {
        viewModelScope.launch {
            _questionResult.emit(Result.Loading)
            try {
                _questionResult.emit(
                    Result.Success<Questions>(
                        questionRepository.getAllQuestions()
                                as Questions
                    )
                )
            } catch (e: Exception) {
                Log.e("View Model", e.localizedMessage)
                _questionResult.emit(Result.Error(e))
            }
        }
    }
}