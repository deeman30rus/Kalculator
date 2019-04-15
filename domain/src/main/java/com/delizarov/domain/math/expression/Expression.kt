package com.delizarov.domain.math.expression

class Expression(
    terms: Collection<Term>
) {
    val terms: Collection<Term> = ArrayList(terms)

    var value: Float? = null

    override fun toString() = terms.joinToString("") { it.toString() }

    fun clone() = Expression(terms).apply {
        value = this@Expression.value
    }


    companion object {
        val EMPTY = Expression(emptyList())
    }
}