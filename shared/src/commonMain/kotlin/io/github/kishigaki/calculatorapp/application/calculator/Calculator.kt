package io.github.kishigaki.calculatorapp.application.calculator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.binding
import io.github.kishigaki.calculatorapp.model.parser.Expression

class Calculator(private val screen: Screen) : KeyPad {

    private var expression: String = ""
        set(value) {
            screen.onExpressionChanged(value)
            val parseResult = Expression().parse(value)
            if (parseResult.isErr) {
                screen.onResultChanged(Err(parseResult.error))
                return
            }
            val calculationResult = parseResult.value.first.calculate()
            if (calculationResult.isErr) {
                screen.onResultChanged(Err(calculationResult.error))
                return
            }
            screen.onResultChanged(Ok(calculationResult.value.roundedValue))
        }

    private var cursorPosition: Int = 0
        set(value) {
            screen.onCursorPositionChanged(value)
        }

    override fun hit(key: Key) {
        when (key) {
            Key.POINT, Key.ZERO, Key.ONE, Key.TWO, Key.THREE, Key.FOUR, Key.FIVE, Key.SIX, Key.SEVEN, Key.EIGHT, Key.NINE -> {
                expression = expression.replaceRange(cursorPosition..cursorPosition, key.text)
                cursorPosition += key.text.length
            }
            Key.PLUS, Key.MINUS, Key.TIMES, Key.DIVIDE -> {
                expression = expression.replaceRange(cursorPosition..cursorPosition, key.text)
                cursorPosition += key.text.length
            }
            Key.OPEN_PARENTHESIS, Key.CLOSE_PARENTHESIS -> {
                expression = expression.replaceRange(cursorPosition..cursorPosition, key.text)
                cursorPosition += key.text.length
            }
            Key.DELETE -> {
                if (cursorPosition > 0) {
                    expression = expression.removeRange(cursorPosition - 1..cursorPosition)
                    cursorPosition -= 1
                }
            }
            Key.EQUAL -> {
                binding {
                    val parseResult = Expression().parse(expression).bind()
                    val calculationResult = parseResult.first.calculate().bind().roundedValue
                    expression = calculationResult.toString()
                    cursorPosition = expression.length
                }
            }
            Key.ALL_CLEAR -> {
                expression = ""
                cursorPosition = 0
            }
            Key.LEFT -> {
                if (cursorPosition > 1) {
                    cursorPosition -= 1
                }
            }
            Key.RIGHT -> {
                if (cursorPosition < expression.length) {
                    cursorPosition += 1
                }
            }
        }
    }
}