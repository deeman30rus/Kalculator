package com.delizarov.fcalc.domain.impl

import com.delizarov.domain.DomainException
import com.delizarov.domain.input.InputInteractor
import com.delizarov.domain.math.misc.isOperator
import com.delizarov.views.com.delizarov.views.keyboard.Key
import com.delizarov.views.com.delizarov.views.keyboard.isFunctional
import com.delizarov.views.com.delizarov.views.keyboard.isNumberPart

class KeyInputInteractor : InputInteractor<Key>() {

    private val lastNumberIsFloat
        get() = lastNumber.contains(".")

    private var lastNumber = ""

    override fun process(input: Key) = when {
        input.isNumberPart() -> addNumberPart(input.toString())
        input.isFunctional() -> when (input) {
            Key.KeyBackspace -> dropLast()
            Key.KeyClear -> clear()
            else -> addOperator(input.toString())
        }
        else -> throw DomainException.UnrecognizedInputException(input.toString())
    }

    private fun addNumberPart(numPart: String) {
        state += when {
            state.isEmpty() && numPart == "." -> {
                lastNumber = "0."
                "0."
            }
            numPart == "." && !lastNumberIsFloat -> {
                lastNumber += numPart
                numPart
            }
            numPart == "." && lastNumberIsFloat -> {
                /*should ignore that case*/
                ""
            }
            numPart == "0" && lastNumber == "0" -> {
                /*should ignore that case*/
                ""
            }
            else -> {
                lastNumber += numPart
                numPart
            }
        }
    }

    private fun dropLast() {

        if (state.isEmpty()) return

        if (lastNumber == "0.") {
            lastNumber = ""
            state = state.dropLast(2)
        } else {
            lastNumber.dropLast(1)
            state = state.dropLast(1)
        }
    }

    private fun clear() {
        state = ""
        lastNumber = ""
    }

    private fun addOperator(input: String) {
        if (state.last().isOperator()) {
            state = state.dropLast(1)
        }

        lastNumber = ""
        state += input
    }
}