package com.delizarov.domain.parser

import com.delizarov.domain.math.expression.Term
import java.lang.IllegalArgumentException

abstract class TermParser<T> {

    @Throws(IllegalArgumentException::class)
    abstract fun parse(source: T): Collection<Term>
}