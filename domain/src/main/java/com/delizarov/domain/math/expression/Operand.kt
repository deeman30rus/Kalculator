package com.delizarov.domain.math.expression

class Operand(
    val value: Float
) : Term()

fun Operand.apply(operator: UnaryOperator) = operator.process(this)