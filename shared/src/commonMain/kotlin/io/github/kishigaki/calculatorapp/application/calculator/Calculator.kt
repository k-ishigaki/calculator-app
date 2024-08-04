package io.github.kishigaki.calculatorapp.application.calculator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.binding
import io.github.kishigaki.calculatorapp.domain.parser.Expression

class Calculator(private val screen: Screen) {

    private var expression: String = ""
        set(value) {
            field = value
            screen.onExpressionChanged(value)
            val parseResult = Expression().parse(value)
            if (parseResult.isErr) {
                screen.onResultChanged(Err(parseResult.error.first))
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
            field = value
            screen.onCursorPositionChanged(value)
        }

    fun hit(key: Key) {
        when (key) {
            Key.POINT, Key.ZERO, Key.ONE, Key.TWO, Key.THREE, Key.FOUR, Key.FIVE, Key.SIX, Key.SEVEN, Key.EIGHT, Key.NINE -> {
                expression = StringBuilder(expression).insert(cursorPosition, key.text).toString()
                cursorPosition += key.text.length
            }
            Key.PLUS, Key.MINUS, Key.TIMES, Key.DIVIDE -> {
                val operatorKeyStrings = listOf(Key.PLUS, Key.MINUS, Key.TIMES, Key.DIVIDE).map { it.text }
                if (operatorKeyStrings.contains(expression.getOrNull(cursorPosition - 1).toString())) {
                    if (operatorKeyStrings.contains(expression.getOrNull(cursorPosition - 2).toString())) {
                        // example: 2*-|10
                        //              ^ <-- cursorPosition
                        if (key == Key.MINUS) {
                            // -> 2*-|10
                        } else {
                            // -> 2+|10
                            expression = StringBuilder(expression).apply {
                                deleteAt(cursorPosition - 1)
                                deleteAt(cursorPosition - 2)
                                insert(cursorPosition - 2, key.text)
                            }.toString()
                            cursorPosition -= 1
                        }
                    } else if (Key.MINUS.text == expression.getOrNull(cursorPosition).toString()) {
                        // example: 2*|-10
                        //             ^ <-- cursorPosition
                        if (key == Key.MINUS) {
                            // -> 2*-|10
                            cursorPosition += 1
                        } else {
                            // -> 2+|10
                            expression = StringBuilder(expression).apply {
                                deleteAt(cursorPosition)
                                deleteAt(cursorPosition - 1)
                                insert(cursorPosition - 1, key.text)
                            }.toString()
                        }
                    } else {
                        // example: 2*|4
                        //             ^ <-- cursorPosition
                        if (listOf(Key.TIMES, Key.DIVIDE).map { it.text }.contains(expression.getOrNull(cursorPosition - 1).toString()) && key == Key.MINUS) {
                            // -> 2*-4
                            expression = StringBuilder(expression).insert(cursorPosition, key.text).toString()
                            cursorPosition += key.text.length
                        } else {
                            // -> 2+4
                            expression = StringBuilder(expression).apply {
                                deleteAt(cursorPosition - 1)
                                insert(cursorPosition - 1, key.text)
                            }.toString()
                        }
                    }
                } else if (operatorKeyStrings.contains(expression.getOrNull(cursorPosition).toString())) {
                    // example: 2|*4
                    //            ^ <-- cursorPosition
                    // -> 2+|4
                    expression = StringBuilder(expression).apply {
                        deleteAt(cursorPosition)
                        insert(cursorPosition, key.text)
                    }.toString()
                    cursorPosition += 1
                } else {
                    expression = StringBuilder(expression).insert(cursorPosition, key.text).toString()
                    cursorPosition += key.text.length
                }
            }
            Key.OPEN_PARENTHESIS, Key.CLOSE_PARENTHESIS -> {
                expression = StringBuilder(expression).insert(cursorPosition, key.text).toString()
                cursorPosition += key.text.length
            }
            Key.DELETE -> {
                if (cursorPosition > 0) {
                    expression = StringBuilder(expression).deleteAt(cursorPosition - 1).toString()
                    cursorPosition -= 1
                }
            }
            Key.EQUAL -> {
                binding {
                    val parseResult = Expression().parse(expression).bind()
                    val calculationResult = parseResult.first.calculate().bind().roundedValue
                    expression = calculationResult.toStringExpanded()
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

    fun moveCursorTo(index: Int) {
        if (!(0..expression.length).contains(index)) {
            throw IndexOutOfBoundsException("moveCursorTo(index) out of range")
        }
        cursorPosition = index
    }
}