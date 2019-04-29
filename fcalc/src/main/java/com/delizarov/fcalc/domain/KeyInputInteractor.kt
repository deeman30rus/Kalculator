package com.delizarov.fcalc.domain

import com.delizarov.domain.DomainException
import com.delizarov.domain.math.misc.isOperator
import com.delizarov.views.com.delizarov.views.keyboard.Key
import com.delizarov.views.com.delizarov.views.keyboard.isFunctional
import com.delizarov.views.com.delizarov.views.keyboard.isNumberPart

sealed class InputAction

class EmptyAction : InputAction()

class AddPartAction(
    val part: String
) : InputAction()

class RemovePartAction : InputAction()

class ClearAction : InputAction()

class ExchangeAction(
    val part: String
) : InputAction()

class KeyInputInteractor {

    var state: String = ""

    private val lastNumberIsFloat
        get() = lastNumber.contains(".")

    private var lastNumber = ""

    fun process(input: Key) = when {
        input.isNumberPart() -> addNumberPart(input.toString())
        input.isFunctional() -> when (input) {
            Key.KeyBackspace -> dropLast()
            Key.KeyClear -> clear()
            else -> addOperator(input.toString())
        }
        else -> throw DomainException.UnrecognizedInputException(input.toString())
    }

    private fun addNumberPart(numPart: String): InputAction {

        val part = when {
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

        if (part.isEmpty()) return EmptyAction()

        state += part
        return AddPartAction(part)
    }

    private fun dropLast(): InputAction {

        if (state.isEmpty()) return EmptyAction()

        if (lastNumber == "0.") {
            lastNumber = ""
            state = state.dropLast(2)
        } else {
            lastNumber.dropLast(1)
            state = state.dropLast(1)
        }

        return RemovePartAction()
    }

    private fun clear(): InputAction {
        state = ""
        lastNumber = ""

        return ClearAction()
    }

    private fun addOperator(input: String): InputAction {
        val action = if (state.last().isOperator()) {
            state = state.dropLast(1)
            ExchangeAction(input)
        } else {
            AddPartAction(input)
        }

        lastNumber = ""
        state += input

        return action
    }
}