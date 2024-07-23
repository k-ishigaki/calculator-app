package io.github.kishigaki.calculatorapp.model.parser

import com.github.michaelbull.result.Result

fun interface Parser<T> {
    fun parse(text: String): Result<Pair<T, String>, Error>
}