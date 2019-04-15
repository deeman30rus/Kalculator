package com.delizarov.domain.math.expression

import java.text.DecimalFormat

class Operand(
    val value: Float
) : Term() {

    override fun equals(other: Any?) = other is Operand && value.equals(other.value)

    override fun hashCode() = value.hashCode()

    override fun toString(): String = DecimalFormat.format(value)

    companion object {

        private val DecimalFormat = DecimalFormat("0.###############")
    }

}

fun Operand.apply(operator: UnaryOperator) = operator.process(this)