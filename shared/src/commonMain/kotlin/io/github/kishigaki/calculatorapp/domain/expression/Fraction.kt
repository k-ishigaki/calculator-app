package io.github.kishigaki.calculatorapp.domain.expression

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode

data class Fraction(val numerator: BigDecimal, val denominator: BigDecimal) : Comparable<Fraction> {

    /**
     * Rounded value of the fraction
     *
     * Decimal precision: 10
     * Rounding mode:`ROUND_HALF_AWAY_FROM_ZERO'
     */
    val roundedValue by lazy { numerator.divide(denominator, DecimalMode(decimalPrecision = 10, roundingMode = RoundingMode.ROUND_HALF_AWAY_FROM_ZERO)) }

    override fun compareTo(other: Fraction): Int {
        val temp = (this - other)
        return (temp.numerator * temp.denominator).intValue(exactRequired = false)
    }

    override fun toString() = "$numerator/$denominator"

    operator fun unaryMinus() = Fraction(-numerator, denominator)

    operator fun plus(other: Fraction) =
        if (this.denominator == other.denominator) Fraction(numerator + other.numerator, denominator)
        else {
            val newDenominator = denominator * other.denominator
            val a = numerator * other.denominator
            val b = other.numerator * denominator
            Fraction(a + b, newDenominator)
        }

    operator fun minus(other: Fraction) = this + -other

    operator fun times(other: Fraction) = Fraction(numerator * other.numerator, denominator * other.denominator)

    operator fun div(other: Fraction) = Fraction(numerator * other.denominator, denominator * other.numerator)

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Fraction) return false
        return numerator * other.denominator == other.numerator * denominator
    }

    override fun hashCode(): Int {
        return 31 * numerator.hashCode() + denominator.hashCode()
    }
}

fun BigDecimal.toFraction() = Fraction(this, BigDecimal.ONE)