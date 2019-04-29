package com.delizarov.fcalc.mvc

import android.view.View
import android.widget.TextView
import com.delizarov.core.mvc.MvcController
import com.delizarov.core.mvc.MvcView
import com.delizarov.core.observable.Cancelable
import com.delizarov.fcalc.R
import com.delizarov.fcalc.domain.*
import com.delizarov.fcalc.ui.views.ExpressionTypeWriter
import com.delizarov.fcalc.vm.ExpressionViewModel
import com.delizarov.views.com.delizarov.views.GridKeyPattern
import com.delizarov.views.com.delizarov.views.TypeWriter
import com.delizarov.views.com.delizarov.views.keyboard.Key
import com.delizarov.views.com.delizarov.views.keyboard.KeyboardView

class CalculatorMvcView(
    view: View
) : MvcView(view) {

    private val typeWriter = view.findViewById<TypeWriter>(R.id.typewriter)
    private val keyboardView = view.findViewById<KeyboardView>(R.id.keyboard)
    private val resultView = view.findViewById<TextView>(R.id.result_view)

    private val expressionTypeWriter = ExpressionTypeWriter(typeWriter)

    init {

        val context = view.context

        keyboardView.onKeyPressed = { key ->
            (listener as EventListener).onKeyboardKeyPressed(key)
        }
        keyboardView.adapter = KeyboardView.Adapter(context, keyboardView, GridKeyPattern.DefaultPattern)
    }

    fun showInputAction(action: InputAction) {

        when(action) {
            is EmptyAction -> { /*do nothing*/ }
            is AddPartAction -> { expressionTypeWriter.addPart(action.part) }
            is RemovePartAction -> { expressionTypeWriter.removeLastPart() }
            is ExchangeAction -> {
                expressionTypeWriter.removeLastPart()
                expressionTypeWriter.addPart(action.part)
            }
            is ClearAction -> { expressionTypeWriter.clear() }
        }
    }

    fun showResult(result: Float?) {
        resultView.text = result?.toString() ?: ""
    }

    interface EventListener : MvcView.EventListener {

        fun onKeyboardKeyPressed(key: Key)
    }

    class Controller : MvcController<CalculatorMvcView>(), EventListener {

        private val vm = ExpressionViewModel()

        private var subscription: Cancelable? = null

        override fun attachView(view: CalculatorMvcView) {

            subscription?.cancel()
            subscription = vm.subscribe(
                { action -> view.showInputAction(action) },
                { res -> view.showResult(res) }
            )
        }

        override fun detachView() {
            subscription?.cancel()
        }

        override fun onKeyboardKeyPressed(key: Key) {

            vm.onKeyPressed(key)
        }
    }
}