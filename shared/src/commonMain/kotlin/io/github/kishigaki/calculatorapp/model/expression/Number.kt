package io.github.kishigaki.calculatorapp.model.expression

import com.github.michaelbull.result.Ok
import com.ionspin.kotlin.bignum.decimal.BigDecimal

class Number(private val value: BigDecimal) : Expression {
    override fun calculate() = Ok(value.toFraction())
}