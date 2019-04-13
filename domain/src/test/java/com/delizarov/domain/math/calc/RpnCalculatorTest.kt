package com.delizarov.domain.math.calc


class RpnCalculatorTest {

//    private val calculator: Calculator = RpnCalculator
//
//    @Test
//    fun `correct expression test`() {
//
//        assertIncorrectCalculations(
//            mapOf(
//                "2+2".toExpression() to 4f,
//                "2*2+2".toExpression() to 6f,
//                "2+2*2+2".toExpression() to 8f,
//                "2+2/2".toExpression() to 3f
//            )
//        )
//    }
//
//    @Test
//    fun `sum test`() {
//
//        assertIncorrectCalculations(
//            mapOf(
//                "2+2".toExpression() to 4f,
//                "2+3+4".toExpression() to 9f,
//                "2+3+4+5".toExpression() to 14f
//            )
//        )
//    }
//
//    @Test
//    fun `subtraction test`() {
//
//        assertIncorrectCalculations(
//            mapOf(
//                "2-2".toExpression() to 0f,
//                "2-3".toExpression() to -1f,
//                "3-2".toExpression() to 1f
//            )
//        )
//    }
//
//    @Test
//    fun `multiplication test`() {
//
//        assertIncorrectCalculations(
//            mapOf(
//                "2*0".toExpression() to 0f,
//                "2*3".toExpression() to 6f,
//                "3*2".toExpression() to 6f,
//                "0*2".toExpression() to 0f
//            )
//        )
//    }
//
//    @Test
//    fun `division test`() {
//
//        assertIncorrectCalculations(
//            mapOf(
//                "2/2".toExpression() to 1f,
//                "2/1".toExpression() to 2f,
//                "1/2".toExpression() to 0.5f
//            )
//        )
//    }
//
//    @Test(expected = IllegalStateException::class)
//    fun `syntax incorrect expressions test`() {
//
//        val expressions = listOf(
//            "2+".toExpression(),
//            "2-".toExpression(),
//            "2*".toExpression(),
//            "2/".toExpression()
//        )
//
//        expressions.forEach { expression ->
//            calculator.calculate(expression)
//        }
//    }
//
//    companion object {
//
//        private fun assertIncorrectCalculations(expressions: Map<Expression, Float>) {
//
//            val calculator: Calculator = RpnCalculator
//            expressions.forEach { expression, result ->
//
//                calculator.calculate(expression)
//
//                assertEquals("assertion failed for ${expression.expr}", result, expression.value)
//
//            }
//        }
//    }
}