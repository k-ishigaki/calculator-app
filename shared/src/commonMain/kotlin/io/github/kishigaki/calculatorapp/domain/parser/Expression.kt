package io.github.kishigaki.calculatorapp.domain.parser

import io.github.kishigaki.calculatorapp.domain.expression.Addition
import io.github.kishigaki.calculatorapp.domain.expression.Division
import io.github.kishigaki.calculatorapp.domain.expression.Expression
import io.github.kishigaki.calculatorapp.domain.expression.Multiplication
import io.github.kishigaki.calculatorapp.domain.expression.Subtraction

class Expression : Parser<Expression> {
    override fun parse(text: String) = AdditionOrSubtraction().parse(text)
}

private class AdditionOrSubtraction : Parser<Expression> {
    override fun parse(text: String) =
        PrioritizedChoice(
            Sequence( MultiplicationOrDivision(), Symbol("+"), AdditionOrSubtraction() ) { Addition(it[0], it[2]) },
            Sequence( MultiplicationOrDivision(), Symbol("-"), AdditionOrSubtraction() ) { Subtraction(it[0], it[2]) },
            MultiplicationOrDivision()
        ).parse(text)
}

private class MultiplicationOrDivision : Parser<Expression> {
    override fun parse(text: String) =
        PrioritizedChoice(
            Sequence( ParentheticalMultiplication(), Symbol("*"), MultiplicationOrDivision() ) { Multiplication(it[0], it[2]) },
            Sequence( ParentheticalMultiplication(), Symbol("/"), MultiplicationOrDivision() ) { Division(it[0], it[2]) },
            ParentheticalMultiplication()
        ).parse(text)
}

private class ParentheticalMultiplication : Parser<Expression> {
    override fun parse(text: String) =
        PrioritizedChoice(
            Sequence( Primary(), Symbol("("), Expression(), Symbol(")"), ParentheticalMultiplication() ) { Multiplication(it[0], Multiplication(it[2], it[4])) },
            Sequence( Primary(), Symbol("("), Expression(), Symbol(")") ) { Multiplication(it[0], it[2]) },
            Sequence( Symbol("("), Expression(), Symbol(")"), ParentheticalMultiplication() ) { Multiplication(it[1], it[3]) },
            Primary()
        ).parse(text)
}

private class Primary : Parser<Expression> {
    override fun parse(text: String) =
        PrioritizedChoice(
            Number(),
            Sequence( Symbol("("), Expression(), Symbol(")") ) { it[1] }
        ).parse(text)
}
