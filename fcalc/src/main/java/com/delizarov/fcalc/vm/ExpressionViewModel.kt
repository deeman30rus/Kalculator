package com.delizarov.fcalc.vm

import com.delizarov.core.observable.Cancelable
import com.delizarov.core.observable.ObservableProperty
import com.delizarov.domain.math.calc.impl.RpnCalculator
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.misc.toExpression
import com.delizarov.fcalc.domain.EmptyAction
import com.delizarov.fcalc.domain.InputAction
import com.delizarov.fcalc.domain.KeyInputInteractor
import com.delizarov.fcalc.repo.HistoryRepository
import com.delizarov.views.com.delizarov.views.keyboard.Key


class ExpressionViewModel {

    var expression = Expression.EMPTY
        set(value) {

            if (field == value) return

            field = value
        }

    val lastActionProperty = ObservableProperty<InputAction>(EmptyAction())
    val resultProperty = ObservableProperty<Float?>(null)

    private val inputInteractor = KeyInputInteractor()
    private val repository = HistoryRepository

    fun onKeyPressed(key: Key) {

        if (key == Key.KeyEquals) {
            try {
                calculateResult()
                repository.add(expression.toString(), expression.clone())
            } catch (ex: IllegalStateException) {
                // just ignore, could be uncompleted input
            }
        } else {
            lastActionProperty.property = inputInteractor.process(key)
            expression = inputInteractor.state.toExpression()

        }
    }

    private fun calculateResult() {
        RpnCalculator.calculate(expression)

        resultProperty.property = expression.value

        updateObservableProperties()
    }

    fun subscribe(
        onKeyPressed: (InputAction) -> Unit,
        onResultChanged: (Float?) -> Unit
    ): Cancelable = Subscription(onKeyPressed, onResultChanged)

    private fun updateObservableProperties() {

        resultProperty.property = expression.value
    }

    private inner class Subscription(
        private var onInputAction: (InputAction) -> Unit,
        private var onResultChanged: (Float?) -> Unit
    ) : Cancelable {

        private val expressionSubscription = lastActionProperty.subscribe { expr ->
            onInputAction.invoke(expr)
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