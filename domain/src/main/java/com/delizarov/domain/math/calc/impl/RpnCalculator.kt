package com.delizarov.domain.math.calc.impl

import com.delizarov.domain.math.calc.Calculator
import com.delizarov.domain.math.calc.proc.Processor
import com.delizarov.domain.math.calc.proc.impl.RpmProcessor
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.expression.Term

object RpnCalculator : Calculator {

    override fun calculate(expr: Expression) {

        val processor: Processor<Collection<Term>> = RpmProcessor()

        expr.value = processor.process(expr.terms)
    }
}