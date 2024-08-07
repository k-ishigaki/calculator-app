package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class BinaryOperations<T>(private val primary: Parser<T>, private vararg val constructionRules: Pair<Symbol, ((T, T) -> T)>) : Parser<T> {
    override fun parse(text: String): Result<Pair<T, String>, Pair<Error, String>> {
        val firstResult = primary.parse(text)
        if (firstResult.isErr) {
            return Err(firstResult.error)
        }

        var parsed: Pair<T, String> = firstResult.value

        var continues = true
        while (continues) {
            continues = false
            constructionRules.forEach { rule ->
                val operatorParsed = rule.first.parse(parsed.second)
                if (operatorParsed.isErr) {
                    return@forEach
                }
                val secondOperandParsed = primary.parse(operatorParsed.value.second)
                if (secondOperandParsed.isErr) {
                    return@forEach
                }
                continues = true
                parsed = rule.second(parsed.first, secondOperandParsed.value.first) to secondOperandParsed.value.second
            }
        }

        return Ok(parsed)
    }
}
