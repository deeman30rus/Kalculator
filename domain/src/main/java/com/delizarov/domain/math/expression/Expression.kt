package com.delizarov.domain.math.expression

class Expression(
    terms: Collection<Term>
) {

    private val terms: Collection<Term> = ArrayList(terms)

    var isDirty = true
        private set

    var value: Float = Float.NEGATIVE_INFINITY
        get() {

            if (isDirty)
                throw IllegalStateException("Expression changed but not calculated")

            return field
        }
        set(value) {
            field = value
            isDirty = false
        }

    fun clone() = Expression(terms).apply {
        isDirty = this@Expression.isDirty
        value = this@Expression.value
    }

    override fun toString() = terms.joinToString {
        when (it) {
            is Operand -> it.value.toString()
            is Operator -> it.toString()
            else -> ""
        }
    }

    companion object {
        val EMPTY = Expression(emptyList())
    }
}