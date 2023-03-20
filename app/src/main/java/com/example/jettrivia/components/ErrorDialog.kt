package com.example.jettrivia.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ErrorDialog(message: String = "hello",
                onClick: () -> Unit = {}) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconButton(
                onClick = { onClick() },
                modifier = Modifier.padding(10.dp)
                    .size(70.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = message,
                modifier = Modifier.padding(10.dp),
                color = Color.Red,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

        }
    }
}