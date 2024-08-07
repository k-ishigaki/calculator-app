package io.github.kishigaki.calculatorapp.domain.expression

import com.github.michaelbull.result.Ok
import com.ionspin.kotlin.bignum.decimal.BigDecimal

class NumberValue(private val value: BigDecimal) : Expression {
    override fun calculate() = Ok(value.toFraction())
}