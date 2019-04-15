package com.delizarov.fcalc.vm

import com.delizarov.core.observable.Cancelable
import com.delizarov.core.observable.ObservableProperty
import com.delizarov.domain.math.calc.impl.RpnCalculator
import com.delizarov.domain.math.expression.Expression


class ExpressionViewModel {

    var expression = Expression.EMPTY
        set(value) {

            if (field == value) return

            field = value

            updateObservableProperties()
        }

    val expressionProperty = ObservableProperty("")
    val resultProperty = ObservableProperty<Float?>(null)

    fun calculateResult() {
        RpnCalculator.calculate(expression)

        resultProperty.property = expression.value

        updateObservableProperties()
    }

    fun subscribe(
        onExpressionChanged: (String) -> Unit,
        onResultChanged: (Float?) -> Unit
    ) = Subscription(onExpressionChanged, onResultChanged)

    private fun updateObservableProperties() {

        expressionProperty.property = expression.toString()
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