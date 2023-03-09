package com.example.jettrivia.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.ui.theme.mDarkPurple
import com.example.jettrivia.ui.theme.mLightGray

@Preview(showBackground = true)
@Composable
fun Question(modifier: Modifier = Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier
            .fillMaxSize(), color = mDarkPurple
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            QuestionsTracker(10, 100)
            DottedLine(pathEffect)
        }
    }
}

@Composable
fun QuestionsTracker(
    counter: Int = 0,
    outOf: Int = 100,
    modifier: Modifier = Modifier
) {
    Text(text = buildAnnotatedString {
        withStyle(ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                SpanStyle(
                    color = mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light,
                        color = mLightGray,
                        fontSize = 14.sp
                    )
                )
                {
                    append("$outOf")
                }
            }
        }
    }, modifier.padding(20.dp))
}

@Composable
fun DottedLine(
    pathEffect: PathEffect,
    modifier: Modifier = Modifier
) {
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