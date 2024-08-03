package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.kishigaki.calculatorapp.domain.expression.Expression

class Symbol(private val string: String) : Parser<Expression> {
    override fun parse(text: String): Result<Pair<Expression, String>, Error> {
        if (text.startsWith(string)) {
            val emptyExpression = object:
                Expression {
                override fun calculate() = Err(Error("cannot calculate at symbol"))
            }
            return Ok(emptyExpression to text.removePrefix(string))
        }
        return Err(Error("symbol not found string = $string"))
    }
}