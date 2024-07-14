package io.github.kishigaki.calculatorapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform