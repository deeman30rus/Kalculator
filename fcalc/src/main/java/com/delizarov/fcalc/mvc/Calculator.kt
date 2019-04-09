package com.delizarov.fcalc.mvc

import android.content.Context
import android.view.View
import android.widget.TextView
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.Cancelable
import com.delizarov.domain.math.expression.Expression
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
        private val context: Context
    ) : MvcController<CalculatorMvcView>(), EventListener {

        private val vm = ExpressionViewModel(Expression(""))

        private val repository = HistoryRepository

        private var subscription: Cancelable? = null

        override fun attachView(view: CalculatorMvcView) {

            subscription?.cancel()
            subscription = vm.subscribe(
                { expr -> view.showExpression(expr)},
                { res -> view.showResult(res)}
            )
        }

        override fun detachView() {
            subscription?.cancel()
        }

        override fun onKeyboardKeyPressed(key: Key) = when (key) {

            Key.Key0, Key.Key1, Key.Key2, Key.Key3, Key.Key4,
            Key.Key5, Key.Key6, Key.Key7, Key.Key8, Key.Key9,
            Key.KeyPlus, Key.KeyMinus, Key.KeyMultiply, Key.KeyDivide, Key.KeyPercent,
            Key.KeyDot -> {
                vm.addStringToExpression(key.toResString(context))
            }

            Key.KeyAC, Key.KeyClear -> vm.clearExpression()

            Key.KeyEquals ->  {

                vm.calculateResult()

                repository.add(vm.expression.expr, vm.expression.clone())
            }

            Key.KeyBackspace -> {
                if (vm.isNotEmpty()) {
                    vm.deleteLastChar()
                } else {
                    // do nothing
                }
            }
        }


    }
}

private fun Key.toResString(context: Context) = context.getString(
    when (this) {
        Key.Key0 -> com.delizarov.views.R.string.key_0
        Key.Key1 -> com.delizarov.views.R.string.key_1
        Key.Key2 -> com.delizarov.views.R.string.key_2
        Key.Key3 -> com.delizarov.views.R.string.key_3
        Key.Key4 -> com.delizarov.views.R.string.key_4
        Key.Key5 -> com.delizarov.views.R.string.key_5
        Key.Key6 -> com.delizarov.views.R.string.key_6
        Key.Key7 -> com.delizarov.views.R.string.key_7
        Key.Key8 -> com.delizarov.views.R.string.key_8
        Key.Key9 -> com.delizarov.views.R.string.key_9
        Key.KeyPlus -> com.delizarov.views.R.string.key_plus
        Key.KeyMinus -> com.delizarov.views.R.string.key_minus
        Key.KeyMultiply -> com.delizarov.views.R.string.key_multiply
        Key.KeyDivide -> com.delizarov.views.R.string.key_divide
        Key.KeyClear -> com.delizarov.views.R.string.key_clear
        Key.KeyAC -> com.delizarov.views.R.string.key_ac
        Key.KeyEquals -> com.delizarov.views.R.string.key_equals
        Key.KeyBackspace -> com.delizarov.views.R.string.key_backspace
        Key.KeyPercent -> com.delizarov.views.R.string.key_percent
        Key.KeyDot -> com.delizarov.views.R.string.key_dot
    }
)