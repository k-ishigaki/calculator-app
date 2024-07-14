package io.github.kishigaki.calculatorapp.application.calculator

import com.github.michaelbull.result.Result
import com.ionspin.kotlin.bignum.decimal.BigDecimal

interface Screen {
    fun onExpressionChanged(expression: String)
    fun onCursorPositionChanged(cursorPosition: Int)
    fun onResultChanged(result: Result<BigDecimal, Error>)
}