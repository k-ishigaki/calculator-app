package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import io.github.kishigaki.calculatorapp.domain.expression.Expression
import io.github.kishigaki.calculatorapp.domain.expression.Number

class Number : Parser<Expression> {
    override fun parse(text: String): Result<Pair<Expression, String>, Error> {
        Regex("""^-?\d+(\.\d+)?([eE][+-]?[0-9]+)?""").find(text)?.let {
            return Ok(Number(BigDecimal.parseString(it.value)) to text.replaceFirst(it.value, ""))
        }
        return Err(Error("\"$text\" is not a number"))
    }
}