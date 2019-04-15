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

        override fun equals(other: Any?): Boolean {
            return other is Negation
        }

        override fun hashCode() = Negation::class.hashCode()
    }
}

abstract class BinaryOperator : Operator() {

    abstract fun process(leftOperand: Operand, rightOperand: Operand): Operand

    object Addition : BinaryOperator() {

        override val priority = 1

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value + rightOperand.value)

        override fun toString() = "+"

        override fun equals(other: Any?) = other is Addition

        override fun hashCode() = Addition::class.hashCode()
    }

    object Subtraction : BinaryOperator() {

        override val priority = 1

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value - rightOperand.value)

        override fun toString() = "-"

        override fun equals(other: Any?) = other is Subtraction

        override fun hashCode() = Subtraction::class.hashCode()
    }

    object Multiplication : BinaryOperator() {

        override val priority = 2

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value * rightOperand.value)

        override fun toString() = "*"

        override fun equals(other: Any?) = other is Multiplication

        override fun hashCode() = Multiplication::class.hashCode()
    }

    object Division : BinaryOperator() {

        override val priority = 2

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value / rightOperand.value)

        override fun toString() = "/"

        override fun equals(other: Any?) = other is Division

        override fun hashCode() = Division::class.hashCode()
    }

    object Reminder : BinaryOperator() {

        override val priority = 3

        override fun process(leftOperand: Operand, rightOperand: Operand) =
            Operand(leftOperand.value % rightOperand.value)

        override fun toString() = "%"

        override fun equals(other: Any?) = other is Reminder

        override fun hashCode() = Reminder::class.hashCode()
    }
}