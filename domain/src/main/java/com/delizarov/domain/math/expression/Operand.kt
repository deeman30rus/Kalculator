package com.delizarov.domain.math.expression

class Operand(
    val value: Float
) : Term() {

    override fun equals(other: Any?) = other is Operand && value.equals(other.value)

    override fun hashCode() = value.hashCode()

    override fun toString() = value.toString()

}

fun Operand.apply(operator: UnaryOperator) = operator.process(this)