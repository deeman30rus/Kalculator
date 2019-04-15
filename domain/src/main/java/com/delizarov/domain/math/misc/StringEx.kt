package com.delizarov.domain.math.misc

import com.delizarov.domain.math.expression.BinaryOperator
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.expression.Operand
import com.delizarov.domain.math.expression.Term
import com.delizarov.domain.parser.impl.StringTermParser

fun String.slice(start: Int, predicate: (Char) -> Boolean): String {

    var p = start

    while (p < length && predicate(get(p)))
        ++p

    return substring(start until p)
}

internal fun String.toTerm(): Term = when {
    this[0].isDigit() -> Operand(this.toFloat())

    this[0] == '+' -> BinaryOperator.Addition
    this[0] == '-' -> BinaryOperator.Subtraction
    this[0] == '/' -> BinaryOperator.Division
    this[0] == '*' -> BinaryOperator.Multiplication
    this[0] == '%' -> BinaryOperator.Reminder

    else -> throw IllegalArgumentException("unknown term $this")
}

fun String.toExpression() = Expression(StringTermParser().parse(this))

fun Char.isFloatingPoint() = this == '.'

fun Char.isOperator() =
    this == '+' ||
            this == '-' ||
            this == '*' ||
            this == '/' ||
            this == '%' ||
            this == '^'

fun Char.isEquals() = this == '='