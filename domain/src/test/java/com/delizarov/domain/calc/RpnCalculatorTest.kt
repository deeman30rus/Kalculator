package com.delizarov.domain.calc

import com.delizarov.domain.math.calc.Calculator
import com.delizarov.domain.math.calc.impl.RpnCalculator
import com.delizarov.domain.math.misc.toExpression
import junit.framework.Assert.assertEquals
import org.junit.Test


class RpnCalculatorTest {

    private val calculator: Calculator = RpnCalculator

    @Test
    fun `correct expression test`() {

        val expressions = mapOf(
            "2+2".toExpression() to 4f,
            "2*2+2".toExpression() to 6f,
            "2+2*2+2".toExpression() to 8f,
            "2+2/2".toExpression() to 3f
        )

        expressions.forEach { expression, result ->

            calculator.calculate(expression)

            assertEquals("assertion failed for ${expression.expr}", result, expression.value)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `syntax incorrect expressions test`() {

        val expressions = listOf(
            "2+".toExpression(),
            "2-".toExpression(),
            "2*".toExpression(),
            "2/".toExpression()
        )

        expressions.forEach { expression ->
            calculator.calculate(expression)
        }
    }
}