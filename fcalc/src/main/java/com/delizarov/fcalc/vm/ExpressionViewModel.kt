package com.delizarov.fcalc.vm

import com.delizarov.core.observable.Cancelable
import com.delizarov.core.observable.ObservableProperty
import com.delizarov.domain.math.calc.impl.RpnCalculator
import com.delizarov.domain.math.expression.Expression


class ExpressionViewModel(
    private var expression: Expression
) {

    val expressionProperty = ObservableProperty(expression.expr)
    val resultProperty = ObservableProperty<Float?>(null)

    fun subscribe(onExpressionChanged: (String) -> Unit,
                  onResultChanged: (Float?) -> Unit) = Subscription(onExpressionChanged, onResultChanged)

    fun isNotEmpty() = !isEmpty()

    fun clearExpression() {
        expression = Expression.EMPTY

        updateObservableProperties()
    }

    fun deleteLastChar() {

        expression.expr.dropLast(1)
        updateObservableProperties()
    }

    fun calculateResult() {
        RpnCalculator.calculate(expression)

        resultProperty.property = expression.value

        updateObservableProperties()
    }

    fun addStringToExpression(str: String) {
        expression.expr += str

        updateObservableProperties()
    }

    private fun isEmpty() = expression.expr.isEmpty()

    private fun updateObservableProperties() {

        expressionProperty.property = expression.expr

        if (!expression.isDirty)
            resultProperty.property = expression.value
    }

    inner class Subscription(
        private var onExpressionChanged: (String) -> Unit,
        private var onResultChanged: (Float?) -> Unit
    ) : Cancelable {

        private val expressionSubscription = expressionProperty.subscribe { expr ->
            onExpressionChanged.invoke(expr)
        }

        private val resultSubscription = resultProperty.subscribe { r ->
            onResultChanged.invoke(r)
        }

        override fun cancel() {
            expressionSubscription.cancel()
            resultSubscription.cancel()
        }
    }
}