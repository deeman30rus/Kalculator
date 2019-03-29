package com.delizarov.fcalc.mvc

import android.content.Context
import android.view.View
import android.widget.TextView
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.CloseableSubscription
import com.delizarov.core.observable.ObservableProperty
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

    class Controller(
        private val context: Context
    ) : MvcController<CalculatorView>(), EventListener {

        private val model = ObservableProperty("")

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
                Key.KeyDot -> model.item += key.toResString(context)

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

private fun Key.toResString(context: Context) = context.getString(
    when(this) {
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