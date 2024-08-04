package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class Sequence<T>(private vararg val parsers: Parser<T>, private val constructor: (List<T>) -> T) : Parser<T> {
    override fun parse(text: String): Result<Pair<T, String>, Pair<Error, String>> {
        var remains = text
        val t = constructor(parsers.map {
            val result = it.parse(remains)
            if (result.isErr) {
                return Err(result.error)
            }
            remains = result.value.second
            return@map result.value.first
        })
        return Ok(t to remains)
    }
}