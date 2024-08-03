package io.github.kishigaki.calculatorapp.domain.expression

import com.github.michaelbull.result.Result
import io.github.kishigaki.calculatorapp.domain.expression.Fraction

interface Expression {
    fun calculate(): Result<Fraction, Error>
}
