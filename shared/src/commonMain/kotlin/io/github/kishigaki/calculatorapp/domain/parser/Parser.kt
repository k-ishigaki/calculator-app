package io.github.kishigaki.calculatorapp.domain.parser

import com.github.michaelbull.result.Result

fun interface Parser<T> {
    fun parse(text: String): Result<Pair<T, String>, Pair<Error, String>>
}