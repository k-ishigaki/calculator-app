package io.github.kishigaki.calculatorapp.model.expression

import com.github.michaelbull.result.binding

class Multiplication(private val a: Expression, private val b: Expression) : Expression {
    override fun calculate() = binding { a.calculate().bind() * b.calculate().bind() }
}