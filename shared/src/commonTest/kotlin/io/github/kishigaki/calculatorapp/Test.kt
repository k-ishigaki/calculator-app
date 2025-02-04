package io.github.kishigaki.calculatorapp

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import io.github.kishigaki.calculatorapp.domain.expression.Addition
import io.github.kishigaki.calculatorapp.domain.expression.Division
import io.github.kishigaki.calculatorapp.domain.expression.Multiplication
import io.github.kishigaki.calculatorapp.domain.expression.NumberValue
import io.github.kishigaki.calculatorapp.domain.expression.Subtraction
import io.github.kishigaki.calculatorapp.domain.parser.Expression
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculationTest {

    @Test
    fun testExpressionCalculation() {
        assertEquals(12.toBigDecimal(), Addition(NumberValue(10.toBigDecimal()), NumberValue(2.toBigDecimal())).calculate().value.roundedValue, "test addition")
        assertEquals(1.34.toBigDecimal(), Subtraction(NumberValue(3.4.toBigDecimal()), NumberValue(2.06.toBigDecimal())).calculate().value.roundedValue, "test subtraction")
        assertEquals(27.0.toBigDecimal(), Multiplication(NumberValue(9.0.toBigDecimal()), NumberValue(3.0.toBigDecimal())).calculate().value.roundedValue, "test multiplication")
        assertEquals(1.11.toBigDecimal(), Division(NumberValue(5.55.toBigDecimal()), NumberValue(5.0.toBigDecimal())).calculate().value.roundedValue, "test division(success)")
        assertTrue(Division(NumberValue(100.0.toBigDecimal()), NumberValue(0.0.toBigDecimal())).calculate().isErr, "test division(failure)")
    }

    @Test
    fun testErrorPropagation() {
        val errorExpression = Division(NumberValue(123.toBigDecimal()), NumberValue(BigDecimal.ZERO))
        assertTrue(Subtraction(errorExpression, NumberValue(456.toBigDecimal())).calculate().isErr, "test left term error")
        assertTrue(Addition(NumberValue(456.toBigDecimal()), errorExpression).calculate().isErr, "test right term error")
    }

    @Test
    fun testParsingExpression() {
        assertEquals(2.toBigDecimal(), Expression().parse("1+1").value.first.calculate().value.roundedValue)
        assertEquals((-12).toBigDecimal(), Expression().parse("-4-8").value.first.calculate().value.roundedValue)
        assertEquals(2222.toBigDecimal(), Expression().parse("101*22").value.first.calculate().value.roundedValue)
        assertEquals(0.5.toBigDecimal(), Expression().parse("1.0/2").value.first.calculate().value.roundedValue)
        assertEquals(33.toBigDecimal(), Expression().parse("(11*3)").value.first.calculate().value.roundedValue)
        assertEquals(12.toBigDecimal(), Expression().parse("4(3)").value.first.calculate().value.roundedValue)
        assertEquals((-34.2).toBigDecimal(), Expression().parse("(-102.6)/3").value.first.calculate().value.roundedValue)
        assertEquals(3.33.toBigDecimal(), Expression().parse("(1.11)3").value.first.calculate().value.roundedValue)
        assertEquals(72.toBigDecimal(), Expression().parse("-2(-12)3").value.first.calculate().value.roundedValue)
        assertEquals(65.toBigDecimal(), Expression().parse("-2(2-12)(3+1/4)").value.first.calculate().value.roundedValue)
        assertEquals(7.272727273.toBigDecimal(), Expression().parse("-2(2-12)/(3-1/4)").value.first.calculate().value.roundedValue)
        assertEquals(14.4.toBigDecimal(), Expression().parse("4*3/6*9-4+2/5").value.first.calculate().value.roundedValue)
    }
}