package com.example.jettrivia.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.model.Questions
import com.example.jettrivia.ui.theme.*

@Composable
fun Question(
    questions: Questions,
    modifier: Modifier = Modifier
) {

    val size = questions.size - 1
    val questionIndex = remember {
        mutableStateOf(0)
    }
    val question = questions[questionIndex.value]

    val onNextClick: (() -> Unit) = {
        questionIndex.value = if (questionIndex.value != size) questionIndex.value + 1 else size
    }

    Surface(
        modifier.fillMaxSize(), color = mDarkPurple
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            ShowProgress(score = questionIndex.value)
            QuestionsTracker(
                questionIndex.value, size
            )
            DottedLine()
            QuestionTab(
                question,
                onNextClick
            )
        }
    }
}

@Composable
fun ShowProgress(
    modifier: Modifier = Modifier,
    score: Int
) {
    val gradient = Brush.linearGradient(listOf(mBrown, mRed, mGreen))
    val progress by remember(score) {
        mutableStateOf(score * 0.0002099f)
    }
    Row(
        modifier
            .fillMaxWidth()
            .padding(
                start = 3.dp,
                end = 3.dp,
                top = 16.dp,
                bottom = 16.dp
            )
            .height(45.dp)
            .border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            mLightPurple,
                            mLightPurple
                        )
                    )
                ),
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(25.dp))
            .background(color = Color.Transparent)
    ) {
        Button(
            onClick = {},
            modifier
                .fillMaxWidth(progress)
                .background(gradient)
                .clip(RoundedCornerShape(25.dp))
                .padding(10.dp),
            enabled = false,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            ), contentPadding = PaddingValues(1.dp),
            shape = RoundedCornerShape(25.dp)
        ) {

        }
    }
}

@Composable
fun QuestionsTracker(
    counter: Int, outOf: Int, modifier: Modifier = Modifier
) {
    Text(text = buildAnnotatedString {
        withStyle(ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                SpanStyle(
                    color = mLightGray, fontWeight = FontWeight.Bold, fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light, color = mLightGray, fontSize = 14.sp
                    )
                ) {
                    append("$outOf")
                }
            }
        }
    }, modifier.padding(20.dp))
}

@Composable
fun DottedLine(
    modifier: Modifier = Modifier
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(10.dp)
    ) {
        this.drawLine(
            color = mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )

    }
}

@Composable
fun QuestionTab(
    question: QuestionItem, onNextClick: () -> Unit, modifier: Modifier = Modifier
) {
    val answerState = remember(question) {
        mutableStateOf<String?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf(question.answer)
    }
    val isCorrectAnswerSelectedState = remember(answerState.value, correctAnswerState.value) {
        mutableStateOf(answerState.value == correctAnswerState.value)
    }

    Column(
        modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp, vertical = 20.dp
            ), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = question.question,
            color = mOffWhite,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Choices(
                isCorrectAnswerSelectedState,
                question.choices.toMutableList(),
                answerState = answerState
            )
            NextButton(
                isCorrectAnswerSelectedState, onNextClick = onNextClick
            )
        }
    }
}

@Composable
fun Choices(
    isCorrectAnswerSelectedState: MutableState<Boolean>,
    choices: MutableList<String>,
    answerState: MutableState<String?>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(
            10.dp
        )
    ) {
        choices.forEach { answer ->
            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .clickable {
                        answerState.value = answer
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .height(40.dp)
                    .background(Color.Transparent)
                    .border(
                        BorderStroke(
                            1.dp,
                            color = if (answer == answerState.value &&
                                !isCorrectAnswerSelectedState.value
                            ) mRed else mLightGray
                        ), shape = RoundedCornerShape(
                            percent = 40
                        )
                    )
                    .fillMaxSize()) {
                RadioButton(
                    selected = answerState.value == answer, onClick = {
                        answerState.value = answer
                    }, modifier = Modifier.padding(
                        horizontal = 5.dp, vertical = 0.dp
                    ), colors = RadioButtonDefaults.colors(
                        selectedColor = if (answerState.value != null && isCorrectAnswerSelectedState.value) mGreen else mRed,
                        unselectedColor = mLightGray
                    )
                )
                val color = if (isCorrectAnswerSelectedState.value &&
                    answer == answerState.value
                ) mGreen else mOffWhite
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = color)) {
                        append(answer)
                    }
                }
                Text(
                    text = annotatedString,
                )
            }
        }
    }
}

@Composable
fun NextButton(
    isCorrectAnswerSelectedState: MutableState<Boolean>,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onNextClick() },
            shape = RoundedCornerShape(4.dp),
            enabled = isCorrectAnswerSelectedState.value,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = mLightBlue,
                disabledBackgroundColor = if (isCorrectAnswerSelectedState.value) mBlue else mLightGray
            )
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.button,
            )
        }
    }
}
