package io.github.kishigaki.calculatorapp.model.expression

import com.github.michaelbull.result.Result

interface Expression {
    fun calculate(): Result<Fraction, Error>
}
