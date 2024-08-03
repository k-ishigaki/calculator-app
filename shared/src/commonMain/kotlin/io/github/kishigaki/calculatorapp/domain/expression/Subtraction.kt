package io.github.kishigaki.calculatorapp.domain.expression

import com.github.michaelbull.result.binding

class Subtraction(private val a: Expression, private val b: Expression) : Expression {
    override fun calculate() = binding { a.calculate().bind() - b.calculate().bind() }
}