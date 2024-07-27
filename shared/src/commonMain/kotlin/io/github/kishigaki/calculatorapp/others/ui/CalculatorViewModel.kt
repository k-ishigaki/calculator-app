package io.github.kishigaki.calculatorapp.others.ui

import androidx.lifecycle.ViewModel
import com.github.michaelbull.result.Result
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import io.github.kishigaki.calculatorapp.application.calculator.Calculator
import io.github.kishigaki.calculatorapp.application.calculator.Key
import io.github.kishigaki.calculatorapp.application.calculator.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _expressionText = MutableStateFlow("")
    val expressionText = _expressionText.asStateFlow()

    private val _cursorPosition = MutableStateFlow(0)
    val cursorPosition = _cursorPosition.asStateFlow()

    private val _resultText = MutableStateFlow("")
    val resultText = _resultText.asStateFlow()

    private val calculator = Calculator(object : Screen {
        override fun onExpressionChanged(expression: String) {
            _expressionText.value = expression
        }

        override fun onCursorPositionChanged(cursorPosition: Int) {
            _cursorPosition.value = cursorPosition
        }

        override fun onResultChanged(result: Result<BigDecimal, Error>) {
            if (result.isOk) {
                _resultText.value = result.value.toStringExpanded()
            } else {
                _resultText.value = result.error.toString()
            }
        }

    })

    fun hit(key: Key) {
        calculator.hit(key)
    }

    fun moveCursorTo(index: Int) {
        calculator.moveCursorTo(index)
    }
}