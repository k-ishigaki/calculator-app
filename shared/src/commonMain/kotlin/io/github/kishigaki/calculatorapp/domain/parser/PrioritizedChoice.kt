package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result

class PrioritizedChoice<T>(private vararg val parsers: Parser<T>) : Parser<T> {
    override fun parse(text: String): Result<Pair<T, String>, Error> {
        var lastError = Error()
        parsers.forEach {
            val result = it.parse(text)
            if (result.isOk) {
                return result
            } else {
                lastError = result.error
            }
        }
        return Err(lastError)
    }
}