package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding

class BinaryOperations<T>(private val primary: Parser<T>, private vararg val constructionRules: Pair<Symbol, ((T, T) -> T)>) : Parser<T> {
    override fun parse(text: String) = binding {
        var parsed: Pair<T, String> = primary.parse(text).bind()

        do {
            val consumed = constructionRules.map { rule ->
                binding {
                    val operatorParsed = rule.first.parse(parsed.second).bind()
                    val secondOperandParsed = primary.parse(operatorParsed.second).bind()
                    parsed = rule.second(
                        parsed.first,
                        secondOperandParsed.first
                    ) to secondOperandParsed.second
                }
            }.any { it.isOk }
        } while (consumed)

        parsed
    }
}
