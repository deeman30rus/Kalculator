package com.delizarov.domain.math.expression

sealed class Operator : Term() {

    abstract val priority: Int
}

abstract class UnaryOperator : Operator() {

    abstract fun process(operand: Operand): Operand

    object Negation : UnaryOperator() {

        override val priority = 3

        override fun process(operand: Operand) =
            Operand(-operand.value)
    }
}

abstract class BinaryOperator : Operator() {

    abstract fun process(leftOperand: Operand, rightOperand: Operand): Operand

    object Addition : BinaryOperator() {

        override val priority = 1

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value + rightOperand.value)
    }

    object Subtraction : BinaryOperator() {

        override val priority = 1

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value - rightOperand.value)
    }

    object Multiplication : BinaryOperator() {

        override val priority = 2

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value * rightOperand.value)
    }

    object Division : BinaryOperator() {

        override val priority = 2

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value / rightOperand.value)
    }

    object Reminder : BinaryOperator() {

        override val priority = 3

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value % rightOperand.value)
    }
}