package io.github.kishigaki.calculatorapp.others.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Screen(expressionText: String, cursorPosition: Int, resultText: String) {
    Column {
        Text(expressionText)
        Text(cursorPosition.toString())
        Text(resultText)
    }
}