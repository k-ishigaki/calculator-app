package io.github.kishigaki.calculatorapp.model.expression

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.binding
import com.ionspin.kotlin.bignum.decimal.BigDecimal

class Division(private val a: Expression, private val b: Expression) : Expression {
    override fun calculate() =
        binding {
            val dividend = a.calculate().bind()
            val divisor = b.calculate().andThen { if (it.numerator == BigDecimal.ZERO) Err(Error("cannot divide by zero")) else Ok(it) }.bind()
            dividend / divisor
        }
}