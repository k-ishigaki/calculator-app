package io.github.kishigaki.calculatorapp.domain.parser

import io.github.kishigaki.calculatorapp.domain.expression.Addition
import io.github.kishigaki.calculatorapp.domain.expression.Division
import io.github.kishigaki.calculatorapp.model.expression.Expression
import io.github.kishigaki.calculatorapp.domain.expression.Multiplication
import io.github.kishigaki.calculatorapp.domain.expression.Subtraction

class Expression : Parser<Expression> {

    private val primary: Parser<Expression> by lazy {
        PrioritizedChoice(
            Number(),
            Sequence(
                Symbol("("),
                Expression(),
                Symbol(")")
            ) { it[1] }
        )
    }

    private val secondary: Parser<Expression> by lazy {
        PrioritizedChoice(
            Sequence(
                primary,
                Symbol("("),
                Expression(),
                Symbol(")"),
                primary
            ) { Multiplication(it[0], Multiplication(it[2], it[4])) },
            Sequence(
                primary,
                Symbol("("),
                Expression(),
                Symbol(")")
            ) { Multiplication(it[0], it[2]) },
            Sequence(
                Symbol("("),
                Expression(),
                Symbol(")"),
                primary
            ) { Multiplication(it[1], it[3]) },
            primary
        )
    }

    private val tertiary: Parser<Expression> by lazy {
        PrioritizedChoice(
            Sequence(
                secondary,
                Symbol("*"),
                secondary
            ) { Multiplication(it[0], it[2]) },
            Sequence(
                secondary,
                Symbol("/"),
                secondary
            ) { Division(it[0], it[2]) },
            secondary
        )
    }

    private val quaternary: Parser<Expression> by lazy {
        PrioritizedChoice(
            Sequence(
                tertiary,
                Symbol("+"),
                tertiary
            ) { Addition(it[0], it[2]) },
            Sequence(
                tertiary,
                Symbol("-"),
                tertiary
            ) { Subtraction(it[0], it[2]) },
            tertiary
        )
    }

    override fun parse(text: String) = quaternary.parse(text)
}