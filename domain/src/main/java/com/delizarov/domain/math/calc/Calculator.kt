package com.delizarov.domain.math.calc

import com.delizarov.domain.math.expression.Expression

interface Calculator {

    fun calculate(expr: Expression)
}