package com.delizarov.domain.math.calc.proc

interface Processor<in T> {

    fun process(expr: T): Float
}