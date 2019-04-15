package com.delizarov.domain.math.expression

import java.text.DecimalFormat

class Expression(
    terms: Collection<Term>
) {
    val terms: Collection<Term> = ArrayList(terms)

    var value: Float? = null

    override fun toString() = terms.joinToString("") {

        val decimalFormat = DecimalFormat("0.###############")
        when (it) {
            is Operand -> decimalFormat.format(it.value)
            is Operator -> it.toString()
            else -> ""
        }
    }

    fun clone() = Expression(terms).apply {
        value = this@Expression.value
    }


    companion object {
        val EMPTY = Expression(emptyList())
    }
}