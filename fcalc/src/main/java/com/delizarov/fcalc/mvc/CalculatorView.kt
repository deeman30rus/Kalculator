package com.delizarov.fcalc.mvc

import android.view.View
import android.widget.TextView
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.CloseableSubscription
import com.delizarov.fcalc.R
import com.delizarov.views.com.delizarov.views.GridKeyPattern
import com.delizarov.views.com.delizarov.views.keyboard.Key
import com.delizarov.views.com.delizarov.views.keyboard.KeyboardView

class CalculatorView(
    view: View,
    eventListener: EventListener
) : MvcView(view, eventListener) {

    private val expressionView = view.findViewById<TextView>(R.id.expression_view)
    private val keyboardView = view.findViewById<KeyboardView>(R.id.keyboard)

    init {

        val context = view.context

        keyboardView.onKeyPressed = {key ->
            eventListener.onKeyboardKeyPressed(key)
        }
        keyboardView.adapter = KeyboardView.Adapter(context, keyboardView, GridKeyPattern.DefaultPattern)
    }

    fun showExpression(expression: String) {
        expressionView.text = expression
    }

    interface EventListener : MvcView.EventListener {

        fun onKeyboardKeyPressed(key: Key)
    }

    class Controller : MvcController<CalculatorView>(), EventListener {

        private val model = CalculationModel("")

        private var subscription: CloseableSubscription? = null

        override fun attachView(view: CalculatorView) {

            subscription?.close()
            subscription = model.subscribe {
                view.showExpression(it)
            }
        }

        override fun detachView() {
            subscription?.close()
        }

        override fun onKeyboardKeyPressed(key: Key) = when(key) {

            Key.Key0, Key.Key1, Key.Key2, Key.Key3, Key.Key4,
                Key.Key5, Key.Key6, Key.Key7, Key.Key8, Key.Key9,
                Key.KeyPlus, Key.KeyMinus, Key.KeyMultiply, Key.KeyDivide, Key.KeyPercent,
                Key.KeyDot -> model.item += key.toString()

            Key.KeyAC, Key.KeyClear -> model.item = ""

            Key.KeyEquals -> model.item = model.item

            Key.KeyBackspace -> {
                if (model.item.isNotEmpty()) {
                    model.item = model.item.slice(0 until model.item.length -1)
                } else {
                    // do nothing
                }
            }
        }
    }
}