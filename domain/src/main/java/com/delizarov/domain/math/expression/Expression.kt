package com.delizarov.domain.math.expression

class Expression(
    expr: String
) {

    var isDirty = true
        private set

    var expr: String = expr
        set(value) {
            isDirty = field != value
            field = value
        }

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
}