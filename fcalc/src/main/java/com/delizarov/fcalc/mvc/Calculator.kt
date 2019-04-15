package com.delizarov.fcalc.mvc

import android.content.Context
import android.view.View
import android.widget.TextView
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.Cancelable
import com.delizarov.domain.input.InputInteractor
import com.delizarov.domain.math.expression.Expression
import com.delizarov.domain.math.misc.toExpression
import com.delizarov.fcalc.R
import com.delizarov.fcalc.repo.HistoryRepository
import com.delizarov.fcalc.vm.ExpressionViewModel
import com.delizarov.views.com.delizarov.views.GridKeyPattern
import com.delizarov.views.com.delizarov.views.keyboard.Key
import com.delizarov.views.com.delizarov.views.keyboard.KeyboardView

class CalculatorMvcView(
    view: View
) : MvcView(view) {

    private val expressionView = view.findViewById<TextView>(R.id.expression_view)
    private val keyboardView = view.findViewById<KeyboardView>(R.id.keyboard)
    private val resultView = view.findViewById<TextView>(R.id.result_view)

    init {

        val context = view.context

        keyboardView.onKeyPressed = { key ->
            (listener as EventListener).onKeyboardKeyPressed(key)
        }
        keyboardView.adapter = KeyboardView.Adapter(context, keyboardView, GridKeyPattern.DefaultPattern)
    }

    fun showExpression(expression: String) {
        expressionView.text = expression
    }

    fun showResult(result: Float?) {
        resultView.text = result?.toString() ?: ""
    }

    interface EventListener : MvcView.EventListener {

        fun onKeyboardKeyPressed(key: Key)
    }

    class Controller(
        private val inputInteractor: InputInteractor<Key>
    ) : MvcController<CalculatorMvcView>(), EventListener {

        private val vm = ExpressionViewModel()

        private val repository = HistoryRepository

        private var subscription: Cancelable? = null

        override fun attachView(view: CalculatorMvcView) {

            subscription?.cancel()
            subscription = vm.subscribe(
                { expr -> view.showExpression(expr) },
                { res -> view.showResult(res) }
            )
        }

        override fun detachView() {
            subscription?.cancel()
        }

        override fun onKeyboardKeyPressed(key: Key) {

            if (key == Key.KeyEquals) {
                vm.calculateResult()
                repository.add(vm.expression.toString(), vm.expression.clone())
            } else {
                inputInteractor.process(key)
                vm.expression = inputInteractor.state.toExpression()
            }
        }
    }
}