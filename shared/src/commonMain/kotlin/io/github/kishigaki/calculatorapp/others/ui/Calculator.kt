package io.github.kishigaki.calculatorapp.others.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.kishigaki.calculatorapp.others.ui.CalculatorViewModel

@Composable
fun Calculator(viewModel: CalculatorViewModel = viewModel { CalculatorViewModel() }) {
    val expressionText = viewModel.expressionText.collectAsState()
    val cursorPosition = viewModel.cursorPosition.collectAsState()
    val resultText = viewModel.resultText.collectAsState()

    MaterialTheme {
        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.inverseSurface)) {
            Column {
                Screen(expressionText.value, cursorPosition.value, resultText.value)
                KeyPad { viewModel.hit(it) }
            }
        }
    }
}