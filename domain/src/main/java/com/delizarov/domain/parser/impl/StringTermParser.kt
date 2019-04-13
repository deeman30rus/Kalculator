package com.delizarov.domain.parser.impl

import com.delizarov.domain.math.expression.Term
import com.delizarov.domain.math.misc.isFloatingPoint
import com.delizarov.domain.math.misc.isOperator
import com.delizarov.domain.math.misc.slice
import com.delizarov.domain.math.misc.toTerm
import com.delizarov.domain.parser.TermParser
import java.lang.IllegalArgumentException

class StringTermParser: TermParser<String>() {

    override fun parse(source: String): Collection<Term> {

        var pos = 0
        val terms = mutableListOf<Term>()
        while (pos < source.length) {

            val termStr = expatTerm(source, pos)
            terms.add(termStr.toTerm())

            pos += termStr.length
        }

        return terms
    }

    companion object {

        private fun expatTerm(str: String, p: Int) = when {

            str[p].isOperator() -> str.slice(p until p + 1)
            str[p].isDigit() || str[p].isFloatingPoint() -> str.slice(p) { c -> c.isFloatingPoint() || c.isDigit() }
            else -> throw IllegalArgumentException("Can't parse symbol ${str[p]}")
        }
    }
}